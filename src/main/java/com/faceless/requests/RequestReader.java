package com.faceless.requests;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class RequestReader
{
	private BufferedReader reader;

	public RequestReader(BufferedReader reader)
	{
		this.reader = reader;
	}

	String readStartLine() throws IOException
	{
		return reader.readLine();
	}

	Map<String, String> readHeaders() throws IOException
	{
		Map<String, String> headers = new HashMap<>();

		String headerLine = reader.readLine();
		while (headerLine != null && !headerLine.isEmpty())
		{
			StringTokenizer tok   = new StringTokenizer(headerLine, ":");
			String          name  = tok.nextToken();
			String          value = tok.nextToken();
			headers.put(name.trim(), value.trim());
			headerLine = reader.readLine();
		}

		return headers;
	}

	String readBody() throws IOException
	{
		StringBuilder builder = new StringBuilder();
		String        line;
		while ((line = reader.readLine()) != null)
		{
			if (builder.length() != 0)
			{
				builder.append("\n");
			}
			builder.append(line);
		}
		return builder.toString();
	}
}
