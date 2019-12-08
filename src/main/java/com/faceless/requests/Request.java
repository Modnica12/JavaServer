package com.faceless.requests;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;
import java.util.StringTokenizer;

public class Request
{
	private RequestReader             reader;
	private String                    method;
	private String                    path;
	private Map<String, String>       headers;
	private Hashtable<String, String> arguments;
	private String                    body;

	public Request(RequestReader reader) throws IOException
	{
		this.reader = reader;
		readStartLine();
		readHeaders();
	}

	private void readStartLine() throws IOException
	{
		String startLine = reader.readStartLine();
		System.out.println(startLine);
		StringTokenizer tok = new StringTokenizer(startLine, " ");
		method = tok.nextToken();
		String   rawPath = tok.nextToken();
		String[] parts   = rawPath.split("\\?");
		path = parts[0];
		if (parts.length > 1)
		{
			String[] argumentEntries = parts[1].split("&");
			arguments = new Hashtable<>();
			for (String argumentEntry : argumentEntries)
			{
				String[] kv = argumentEntry.split("=");
				arguments.put(kv[0], kv[1]);
			}
		}
	}

	private void readHeaders() throws IOException
	{
		headers = reader.readHeaders();
	}

	public String getMethod()
	{
		return method;
	}

	public String getPath()
	{
		return path;
	}

	public Map<String, String> getHeaders()
	{
		return headers;
	}

	public Hashtable<String, String> getArguments()
	{
		return arguments;
	}

	public String getArgumentValue(String name)
	{
		return arguments.get(name);
	}

	public String getBody() throws IOException
	{
		if (body == null)
		{
			body = reader.readBody();
		}
		return body;
	}
}
