package com.faceless.handlers;

import com.faceless.Application;
import com.faceless.containers.PropertyContainer;
import com.faceless.requests.Request;
import com.faceless.requests.RequestHandler;
import com.faceless.responses.Response;
import com.google.gson.JsonPrimitive;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginHandler extends RequestHandler
{
	@Override
	public void handle(Request request, Response response, PropertyContainer propertyContainer) throws IOException
	{
		if (!assertRightMethod("POST", request, response))
			return;

		String login    = request.getArgumentValue("login");
		int password = request.getArgumentValue("password").hashCode();
		String getPassword = "SELECT password from users WHERE login = '" + login + "';";

		ResultSet dbPassword = Application.authServer.database.executeQuery(getPassword);

		try {
			if (!dbPassword.next()) {
				String stmt =
						"INSERT IGNORE INTO users(login, password)\n" +
								"   VALUE ('" + login + "','" + password + "')\n"+
								"ON DUPLICATE KEY UPDATE login=login";
				Application.authServer.database.executeUpdate(stmt);
			}
			else{
				String thisPassword = dbPassword.getString("password");
				if (Integer.parseInt(thisPassword) != password){
					System.out.println("Incorrect");
					response.setStatus("401");
					response.setDescription("Incorrect password");
					return;
				}
			}
			System.out.println("CORRECT PASSWORD");
			response.setStatus("200");
			response.setDescription("OK");
			response.writeResponse(new JsonPrimitive(true).getAsString());

		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}
