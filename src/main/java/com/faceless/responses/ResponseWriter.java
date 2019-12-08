package com.faceless.responses;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Map;

public class ResponseWriter
{
	private BufferedWriter writer;

	public ResponseWriter(BufferedWriter writer)
	{
		this.writer = writer;
	}

	void writeStartLine(String status, String description) throws IOException
	{
		writer.write("HTTP/1.1 ");
		writer.write(status);
		if (description != null)
		{
			writer.write(" ");
			writer.write(description);
		}
		writer.newLine();
	}

	void writeHeaders(Map<String, String> headers) throws IOException
	{
		for (Map.Entry<String, String> header : headers.entrySet())
		{
			writer.write(header.getKey());
			writer.write(": ");
			writer.write(header.getValue());
			writer.newLine();
		}
		writer.newLine();
	}

	void writeBody(String body) throws IOException
	{
		writer.write(body);
	}

	void flush() throws IOException
	{
		writer.flush();
	}
}
