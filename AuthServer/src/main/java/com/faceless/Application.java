package com.faceless;

public class Application
{
	public static AuthServer authServer;

	public static void main(String[] args)
	{
		run();
	}

	private static void run()
	{
		try
		{
			authServer = new AuthServer();
			authServer.runServer();
		}
		catch (Throwable throwable)
		{
			throwable.printStackTrace();
		}
	}
}
