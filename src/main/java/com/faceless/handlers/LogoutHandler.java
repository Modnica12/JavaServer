package com.faceless.handlers;

import com.faceless.Application;
import com.faceless.containers.PropertyContainer;
import com.faceless.requests.Request;
import com.faceless.requests.RequestHandler;
import com.faceless.responses.Response;

import java.io.IOException;

public class LogoutHandler extends RequestHandler
{
	@Override
	public void handle(Request request, Response response, PropertyContainer propertyContainer) throws IOException
	{
		if (!assertRightMethod("POST", request, response))
			return;


		propertyContainer.setProperty("logged_in", "false");
		propertyContainer.removeProperty("login");
		propertyContainer.removeProperty("password");
		response.setStatus("200");
		response.setDescription("OK");
		response.writeResponse(Application.server.loginPageDocument.toString());
	}
}
