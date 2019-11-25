package com.faceless.handlers;

import com.faceless.Application;
import com.faceless.Utilities;
import com.faceless.containers.PropertyContainer;
import com.faceless.requests.Request;
import com.faceless.requests.RequestHandler;
import com.faceless.responses.Response;

import java.io.IOException;

public class 	MainHandler extends RequestHandler
{
	@Override
	public void handle(Request request, Response response, PropertyContainer propertyContainer) throws IOException
	{
		if (!assertRightMethod("GET", request, response))
			return;

		Utilities.applyPropertyContainer(Application.server.mainPageDocument, propertyContainer);
		response.setStatus("200");
		response.setDescription("OK");
		response.writeResponse(Application.server.mainPageDocument.toString());
	}
}
