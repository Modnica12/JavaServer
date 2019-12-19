package com.faceless.tests;

import com.faceless.Application;

public class RunServer
{
    public static void run() throws IllegalStateException, Exception
    {
        Thread thread = new Thread(() -> Application.main(" ".split(" ")));
        thread.setDaemon(true);
        thread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new IllegalStateException("Server hasn't run");
        }
    }
}
