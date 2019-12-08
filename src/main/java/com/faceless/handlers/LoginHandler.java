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
		int password = request.getArgumentValue("password").hashCode();
		String stmt =
				"INSERT IGNORE INTO users(login, password)\n" +
						"   VALUE ('" + login + "','" + password + "')\n"+
						"ON DUPLICATE KEY UPDATE login=login";
		Application.server.database.executeUpdate(stmt);
		propertyContainer.setProperty("logged_in", "true");
		propertyContainer.setProperty("login", login);
		propertyContainer.setProperty("password", password);
		response.setStatus("200");
		response.setDescription("OK");
		response.writeResponse(new JsonPrimitive(true).getAsString());
	}
}
