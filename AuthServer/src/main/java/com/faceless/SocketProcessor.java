package com.faceless;

import com.faceless.requests.Request;
import com.faceless.requests.RequestHandler;
import com.faceless.requests.RequestReader;
import com.faceless.responses.Response;
import com.faceless.responses.ResponseWriter;
import com.faceless.containers.PropertyContainer;

import java.io.*;
import java.net.Socket;

public class 	SocketProcessor implements Runnable
{
	private Socket     socket;
	private AuthServer authServer;

	SocketProcessor(AuthServer server, Socket socket)
	{
		this.authServer = server;
		this.socket = socket;
	}

	public void run() throws java.lang.ArrayIndexOutOfBoundsException
	{
		try
		{
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

			RequestReader  requestReader  = new RequestReader(bufferedReader);
			ResponseWriter responseWriter = new ResponseWriter(bufferedWriter);

			Request  request  = new Request(requestReader);
			Response response = new Response(responseWriter);

			PropertyContainer container = getContainerFor(request);

			RequestHandler requestHandler = authServer.mapper.getHandler(request.getPath());
			if (requestHandler == null)
			{
				System.out.println("Handler not found for path " + request.getPath());
				response.setStatus("404");
				response.setDescription("Not Found");
				response.writeResponse("404 NOT FOUND");
				socket.close();
				return;
			}
			requestHandler.handle(request, response, container);
			socket.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private PropertyContainer getContainerFor(Request request) {
		String id = getUserIdFromRequest(request);
		System.out.println(id);
		if(authServer.propertyContainers.containsKey(id))
			return authServer.propertyContainers.get(id);
		PropertyContainer container = new PropertyContainer();
		container.setProperty("logged_in", false);
		container.setProperty("counter", 0);
		authServer.propertyContainers.put(id, container);
		return container;
	}

	private String getUserIdFromRequest(Request request) {
		return request.getHeaders().get("User-Agent");
	}

}