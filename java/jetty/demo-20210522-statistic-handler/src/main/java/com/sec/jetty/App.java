package com.sec.jetty;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.StatisticsHandler;

/**
 * Hello world!
 * Server
 * └── StatisticsHandler
 *     └── ContextHandlerCollection
 *         ├── ContextHandler 1
 *         :── ...
 *         └── ContextHandler N
 */
public class App {
    public static Server server;

    public static void main( String[] args ) throws Exception {
        server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8080);
        server.addConnector(connector);

        StatisticsHandler statistic = new StatisticsHandler();
        server.setHandler(statistic);

        ContextHandlerCollection contexts = new ContextHandlerCollection();
        statistic.setHandler(contexts);

        ContextHandler contextHandler = new ContextHandler();
        contextHandler.setContextPath("/stat");
        contextHandler.setHandler(new MyStatisticHandler());
        contexts.addHandler(contextHandler);


        server.start();

        //TODO: statistic 怎么用？
    }
}
