package com.sec.jetty;


import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.server.handler.gzip.GzipHandler;
import org.eclipse.jetty.util.resource.Resource;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception {
        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8080);
        server.addConnector(connector);

// Create and configure GzipHandler.

        GzipHandler gzipHandler = new GzipHandler();
// Only compress response content larger than this.
        gzipHandler.setMinGzipSize(1024);
// Do not compress these URI paths.
        gzipHandler.setExcludedPaths("/uncompressed");
// Also compress POST responses.
        gzipHandler.addIncludedMethods("GET");
// Do not compress these mime types.
        gzipHandler.addExcludedMimeTypes("font/ttf");




        ResourceHandler handler = new ResourceHandler();
// Configure the directory where static resources are located.
        handler.setBaseResource(Resource.newResource("d:\\Temp"));
// Configure directory listing.
        handler.setDirectoriesListed(false);
// Configure welcome files.
        handler.setWelcomeFiles(new String[]{"index.html"});
// Configure whether to accept range requests.
        handler.setAcceptRanges(true);
        gzipHandler.setHandler(handler);

// Link the GzipHandler to the Server.
        server.setHandler(gzipHandler);

        server.start();

    }
}
