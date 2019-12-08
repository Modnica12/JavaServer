package com.faceless.requests;

import com.faceless.containers.PropertyContainer;
import com.faceless.responses.Response;

import java.io.IOException;

public abstract class RequestHandler
{
	public abstract void handle(Request request, Response response, PropertyContainer propertyContainer) throws IOException;

	public static boolean assertRightMethod(String method, Request request, Response response) throws IOException
	{
		if (!method.equalsIgnoreCase(request.getMethod()))
		{
			System.out.println("Method not allowed");
			response.setStatus("405");
			response.setDescription("Method Not Allowed");
			response.writeResponse("");
			return false;
		}
		return true;
	}
}
