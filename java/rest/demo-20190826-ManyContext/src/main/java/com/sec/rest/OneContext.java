package com.sec.rest;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;

public class OneContext {
    public static void main(String args[]) throws Exception{
        Server server = new Server(8080);


        ContextHandler contextHandler = new ContextHandler();
        contextHandler.setContextPath("/hello");
        contextHandler.setHandler(new HelloWorldHandler());


        ContextHandler contextHandler2 = new ContextHandler("/world");
        contextHandler2.setHandler(new HelloWorldHandler("world"));

        ContextHandler contextHandler3 = new ContextHandler("/guoqingyun");
        contextHandler3.setHandler(new HelloWorldHandler("guoqingyun"));

        ContextHandler contextHandler4 = new ContextHandler("/lilu");
        contextHandler4.setHandler(new HelloWorldHandler("lilu"));

        ContextHandler contextHandler5 = new ContextHandler("/youandme");
        contextHandler5.setHandler(new HelloWorldHandler("youandme"));


        ContextHandlerCollection collection = new ContextHandlerCollection();
        collection.setHandlers(new Handler[]{contextHandler,contextHandler2,contextHandler3,contextHandler4,contextHandler5});


        server.setHandler(collection);

        server.start();
        server.join();
    }
}
