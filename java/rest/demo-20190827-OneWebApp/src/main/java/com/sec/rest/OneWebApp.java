package com.sec.rest;

import org.eclipse.jetty.jmx.MBeanContainer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

import java.io.File;
import java.lang.management.ManagementFactory;

public class OneWebApp {
    public static void main(String args[]) throws Exception {
        Server server = new Server(8080);


        MBeanContainer mBeanContainer = new MBeanContainer(
                ManagementFactory.getPlatformMBeanServer()
        );
        server.addBean(mBeanContainer);


        WebAppContext context = new WebAppContext();
        context.setContextPath("/");
        context.setWar("hello.war");


        server.setHandler(context);



        server.start();
        server.join();
    }
}
