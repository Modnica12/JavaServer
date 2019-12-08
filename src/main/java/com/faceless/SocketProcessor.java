package com.faceless;

import com.faceless.requests.Request;
import com.faceless.requests.RequestHandler;
import com.faceless.requests.RequestReader;
import com.faceless.responses.Response;
import com.faceless.responses.ResponseWriter;

import java.io.*;
import java.net.Socket;

public class SocketProcessor implements Runnable
{
	private Socket     socket;
	private HttpServer server;

	SocketProcessor(HttpServer server, Socket socket)
	{
		this.server = server;
		this.socket = socket;
	}

	public void run()
	{
		try
		{
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

			RequestReader  requestReader  = new RequestReader(bufferedReader);
			ResponseWriter responseWriter = new ResponseWriter(bufferedWriter);

			Request  request  = new Request(requestReader);
			Response response = new Response(responseWriter);

			RequestHandler requestHandler = server.mapper.getHandler(request.getPath());
			if (requestHandler == null)
			{
				System.out.println("Handler not found for path " + request.getPath());
				response.setStatus("404");
				response.setDescription("Not Found");
				response.writeResponse("404 NOT FOUND");
				socket.close();
				return;
			}
			requestHandler.handle(request, response, Application.server.propertyContainer);
			socket.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}