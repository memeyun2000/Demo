package com.sec.jetty;


import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.server.handler.gzip.GzipHandler;
import org.eclipse.jetty.util.resource.Resource;

/**
 * Hello world!
 *
 */
public class AppDemo20210520
{
    public static void main( String[] args ) throws Exception {
        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8080);
        server.addConnector(connector);

        GzipHandler gzipHandler = new GzipHandler();
        gzipHandler.addIncludedMethods("GET");
        gzipHandler.setMinGzipSize(1024);

        HandlerCollection handlerCollection = new HandlerCollection();
        gzipHandler.setHandler(handlerCollection);


        ResourceHandler resource = new ResourceHandler();
        resource.setBaseResource(Resource.newResource("/apps/demo/temp"));
        handlerCollection.addHandler(resource);


        server.setHandler(gzipHandler);
        server.start();
    }
}
