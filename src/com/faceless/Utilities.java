package com.faceless;

import com.faceless.containers.PropertyContainer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;


public class Utilities
{
	static Document readDocument(String fileName)
	{
		String text = readFile(fileName);
		return insertButtons(parseDocument(text));
	}

	private static Document insertButtons(Document document)
	{
		for (Element input : document.select("fbutton"))
		{
			String buttonName = input.attr("name");
			String method     = input.attr("method");
			String path       = input.attr("path");
			String text       = input.attr("text");
			input.append(createButton(buttonName, method, path, text));
		}

		return document;
	}

	private static String createButton(String name, String method, String path, String text)
	{
		return "<button class=\"" + name + "\" >" + text + "</button>" +
			   "<script>" +
			   "$('." + name + "').click(function ()" +
			   "{$.ajax({url: \"" + path + "\",type: \"" + method + "\"," +
			   "success: function (result) {location.reload();console.log(result)}," +
			   "error: function (error) {console.log(`Error ${error}`)}})})" +
			   "</script>";
	}

	private static String readFile(String fileName)
	{
		try
		{
			var fileStream     = new FileReader(fileName);
			var bufferedReader = new BufferedReader(fileStream);
			return bufferedReader.lines().reduce("", (a, b) -> a + "\n" + b);//aligning all lines of the file
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		System.out.println("Err");
		return "";
	}

	private static Document parseDocument(String htmlFile)
	{
		return Jsoup.parse(htmlFile);
	}

	public static void applyPropertyContainer(Document document, PropertyContainer container)
	{
		if (container == null)
			return;
		for (Element input : document.select("property"))
		{
			String propertyName = input.attr("name");
			input.text(container.getProperty(propertyName));
		}
	}


}
