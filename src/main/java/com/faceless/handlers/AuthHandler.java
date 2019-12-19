package com.faceless.handlers;

import com.faceless.Application;
import com.faceless.containers.PropertyContainer;
import com.faceless.requests.Request;
import com.faceless.requests.RequestHandler;
import com.faceless.responses.Response;
import com.google.gson.JsonPrimitive;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

public class AuthHandler extends RequestHandler
{
	private static final HttpClientBuilder builder = HttpClientBuilder.create();
	private static final String url = "http://localhost:8081";

	@Override
	public void handle(Request request, Response response, PropertyContainer propertyContainer) throws IOException {
		if (!assertRightMethod("POST", request, response))
			return;

		String login    = request.getArgumentValue("login");
		String  password = request.getArgumentValue("password");
		HttpUriRequest loginRequest = new HttpPost(url + "/login?login=" + login + "&password="+ password);
		HttpResponse loginResponse = builder.build().execute(loginRequest);
		System.out.println("CORRECT PASSWORD");

		if (loginResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
			propertyContainer.setProperty("logged_in", "true");
			propertyContainer.setProperty("login", login);
			propertyContainer.setProperty("password", password);
			response.setStatus("200");
			response.setDescription("OK");
			response.writeResponse(new JsonPrimitive(true).getAsString());

		}
		else {
			response.setStatus("401");
			response.setDescription("Incorrect password");
			// return;
		}
	}
}