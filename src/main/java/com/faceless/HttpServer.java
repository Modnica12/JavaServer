package com.faceless;

import com.faceless.containers.PropertyContainer;
import com.faceless.handlers.*;
import com.faceless.requests.RequestMapper;
import com.faceless.sql.Database;
import org.jsoup.nodes.Document;
import java.util.Hashtable;

import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer
{
	private static final int               PORT                = 8080;
	public final         Hashtable<String, PropertyContainer> propertyContainers   = new Hashtable<>();
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
			System.out.println("[INFO]Client accepted");
			try {
				new Thread(new SocketProcessor(this, socket)).start();//starting new thread to process request
			}
			catch (java.lang.ArrayIndexOutOfBoundsException e){
				System.err.println("Write VM name");
			}
		}
	}

	private void loadProperties()
	{
		int initialNumber = 25;
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
		mapper.registerHandler("/login", new AuthHandler());
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