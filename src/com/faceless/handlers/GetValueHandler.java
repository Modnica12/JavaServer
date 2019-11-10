package com.faceless.handlers;

import com.faceless.containers.PropertyContainer;
import com.faceless.requests.Request;
import com.faceless.requests.RequestHandler;
import com.faceless.responses.Response;
import com.google.gson.JsonPrimitive;

import java.io.IOException;

public class GetValueHandler extends RequestHandler
{
	@Override
	public void handle(Request request, Response response, PropertyContainer propertyContainer) throws IOException
	{
		if (!assertRightMethod("GET", request, response))
			return;

		String valueName = request.getArgumentValue("name");
		System.out.println(valueName);
		if (propertyContainer.hasProperty(valueName))
		{
			System.out.println(propertyContainer.getProperty(valueName));
			response.setStatus("200");
			response.setDescription("OK");
			response.setJsonResponse();
			String value = propertyContainer.getProperty(valueName);
			response.writeResponse(new JsonPrimitive(value).getAsString());
		}
		else
		{
			response.setStatus("404");
			response.setDescription("Value not found");
		}
	}
}
