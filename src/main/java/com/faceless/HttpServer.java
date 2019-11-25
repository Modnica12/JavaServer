package com.faceless;

import com.faceless.containers.PropertyContainer;
import com.faceless.handlers.*;
import com.faceless.requests.RequestMapper;
import com.faceless.sql.Database;
import org.jsoup.nodes.Document;

import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer
{
	private static final int               PORT                = 8080;
	public final         PropertyContainer propertyContainer   = new PropertyContainer();
	final                RequestMapper     mapper              = new RequestMapper();
	public               Document          mainPageDocument    = Utilities.readDocument("mainpage.html");
	public               Document          loginPageDocument   = Utilities.readDocument("loginpage.html");
	public               Document          orderVMPageDocument = Utilities.readDocument("order_vm.html");
	public               Document          lookVMPageDocument  = Utilities.readDocument("look_vm.html");
	public               Database          database;

	void runServer(String... args) throws Throwable
	{
		loadProperties();

		ServerSocket ss = new ServerSocket(PORT);//select socket to accept requests from
		//noinspection InfiniteLoopStatement
		while (true)
		{
			Socket socket = ss.accept();//lock until request accepted
			System.err.println("[INFO]Client accepted");
			new Thread(new SocketProcessor(this, socket)).start();//starting new thread to process request
		}
	}

	private void loadProperties()
	{
		int initialNumber = 25;
		propertyContainer.setProperty("counter", Integer.toString(initialNumber));
		propertyContainer.setProperty("logged_in", false);
		mapper.registerHandler("/", new MainHandler());
		mapper.registerHandler("/click", new ClickButtonHandler(1));
		mapper.registerHandler("/unclick", new ClickButtonHandler(-1));
		mapper.registerHandler("/reset", new ResetingHandler(initialNumber));
		mapper.registerHandler("/get", new GetValueHandler());
		mapper.registerHandler("/set", new SetValueHandler());
		mapper.registerHandler("/new", new NewValueHandler());
		mapper.registerHandler("/del", new DeleteValueHandler());
		mapper.registerHandler("/loginpage", new LoginPageHandler());
		mapper.registerHandler("/vmorderpage", new OrderVMPageHandler());
		mapper.registerHandler("/vmlookpage", new LookVMPageHandler());
		mapper.registerHandler("/login", new LoginHandler());
		mapper.registerHandler("/logout", new LogoutHandler());
		mapper.registerHandler("/ordervm", new CreateVMHandler());
		mapper.registerHandler("/myvms", new VmListHandler());
		mapper.registerHandler("/removevm", new RemoveVMHandler());
		mapper.registerHandler("/editvm", new VMCharacteristicsHandler());

		try
		{
			database = new Database();
			database.connect();
		}
		catch (ClassNotFoundException e)
		{
			System.out.println("ClassNotFoundException");
		}
	}
}