package com.faceless.requests;

import java.util.Hashtable;

public class RequestMapper
{
	private Hashtable<String, RequestHandler> handlers = new Hashtable<>();

	public void registerHandler(String path, RequestHandler handler)
	{
		handlers.put(path, handler);
	}

	public RequestHandler getHandler(String path)
	{
		return handlers.get(path);
	}
}
