package com.sec.rest;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;

public class OneContext {
    public static void main(String args[]) throws Exception{
        Server server = new Server(8080);


        ContextHandler contextHandler = new ContextHandler();
        contextHandler.setContextPath("/hello");
        contextHandler.setHandler(new HelloWorldHandler());



        server.setHandler(contextHandler);


        server.start();
        server.join();
    }
}
