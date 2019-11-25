package com.faceless.handlers;

import com.faceless.Application;
import com.faceless.containers.PropertyContainer;
import com.faceless.requests.Request;
import com.faceless.requests.RequestHandler;
import com.faceless.responses.Response;

import java.io.IOException;

public class DeleteValueHandler extends RequestHandler
{
	@Override
	public void handle(Request request, Response response, PropertyContainer propertyContainer) throws IOException
	{
		if (!assertRightMethod("DELETE", request, response))
			return;

		String valueName = request.getArgumentValue("name");

		if (Application.server.propertyContainer.hasProperty(valueName))
		{
			Application.server.propertyContainer.removeProperty(valueName);
			response.setStatus("200");
			response.setDescription("OK");
			response.setJsonResponse();
			response.writeResponse("{\n\t\"success\" : \"True\"\n}");
		}
		else
		{
			response.setStatus("404");
			response.setDescription("Value is not registered");
			response.writeResponse("{\n\t\"success\" : \"False\"\n}");
		}
	}
}
