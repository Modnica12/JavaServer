package com.faceless.handlers;

import com.faceless.containers.PropertyContainer;
import com.faceless.requests.Request;
import com.faceless.requests.RequestHandler;
import com.faceless.responses.Response;

import java.io.IOException;

public class NewValueHandler extends RequestHandler
{
	@Override
	public void handle(Request request, Response response, PropertyContainer propertyContainer) throws IOException
	{
		if (!assertRightMethod("PUT", request, response))
			return;


		String valueName = request.getArguments().keys().nextElement();
		String value     = request.getArgumentValue(valueName);

		if (!propertyContainer.hasProperty(valueName))
		{
			propertyContainer.setProperty(valueName, value);
			response.setStatus("201");
			response.setDescription("OK");
			response.setJsonResponse();
			response.writeResponse("{\n\t\"" + valueName + "\" : \"" +
								   propertyContainer.getProperty(valueName) + "\"\n}");
		}
		else
		{
			response.setStatus("403");
			response.setDescription("Value is already registered");
		}
	}
}
