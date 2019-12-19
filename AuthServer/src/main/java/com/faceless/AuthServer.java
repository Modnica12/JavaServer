package com.faceless;

import com.faceless.containers.PropertyContainer;
import com.faceless.handlers.*;
import com.faceless.requests.RequestMapper;
import com.faceless.sql.Database;
import org.jsoup.nodes.Document;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;

public class AuthServer {
    private static final int               PORT                = 8081;
    public final Hashtable<String, PropertyContainer> propertyContainers   = new Hashtable<>();
    final RequestMapper mapper              = new RequestMapper();
    public Database database;

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
        //mapper.registerHandler("/loginpage", new LoginPageHandler());
        mapper.registerHandler("/login", new LoginHandler());
        //mapper.registerHandler("/logout", new LogoutHandler());

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
