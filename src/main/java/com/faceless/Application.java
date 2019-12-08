package com.faceless;

public class Application
{
	public static HttpServer server;

	public static void main(String[] args)
	{
		run();
	}

	private static void run()
	{
		try
		{
			server = new HttpServer();
			server.runServer();
		}
		catch (Throwable throwable)
		{
			throwable.printStackTrace();
		}
	}
}
