package com.faceless.handlers;

import com.faceless.Application;
import com.faceless.containers.PropertyContainer;
import com.faceless.requests.Request;
import com.faceless.requests.RequestHandler;
import com.faceless.responses.Response;
import com.google.gson.JsonPrimitive;

import java.io.IOException;

public class LoginHandler extends RequestHandler
{
	@Override
	public void handle(Request request, Response response, PropertyContainer propertyContainer) throws IOException
	{
		if (!assertRightMethod("POST", request, response))
			return;

		String login    = request.getArgumentValue("login");
		String password = request.getArgumentValue("password");
		String stmt =
				"INSERT INTO users(login, password) " +
				"VALUE ('" + login + "','" + password + "');";
		Application.server.database.executeUpdate(stmt);
		propertyContainer.setProperty("logged_in", "true");
		propertyContainer.setProperty("login", login);
		propertyContainer.setProperty("password", password);
		response.setStatus("200");
		response.setDescription("OK");
		response.writeResponse(new JsonPrimitive(true).getAsString());
	}
}
