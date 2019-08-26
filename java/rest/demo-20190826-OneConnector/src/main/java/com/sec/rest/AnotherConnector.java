package com.sec.rest;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;

public class AnotherConnector {
    public static  void main(String args[]) throws Exception{
        Server server = new Server();


        ServerConnector http8080 = new ServerConnector(server);
        http8080.setIdleTimeout(30 * 1000);
        http8080.setPort(8080);
        http8080.setHost("localhost");


        ServerConnector http8090 = new ServerConnector(server);
        http8090.setIdleTimeout(30 * 1000);
        http8090.setPort(8090);
        http8090.setHost("localhost");



        server.addConnector(http8080);
        server.addConnector(http8090);

        server.setHandler(new HelloHandler());



        server.start();
        server.join();

    }
}
