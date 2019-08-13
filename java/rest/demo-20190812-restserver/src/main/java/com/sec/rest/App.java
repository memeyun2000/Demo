package com.sec.rest;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.ws.rs.core.Application;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 *
 */
public class App extends Application{
    private static Logger LOG = LoggerFactory.getLogger(App.class);
    private static Server jettyWebServer;

    public static void main(String[] args) {
        System.out.println("Hello World!");

        jettyWebServer = setupJettyServer();

        ContextHandlerCollection contexts = new ContextHandlerCollection();
        jettyWebServer.setHandler(contexts);
        WebAppContext webapp = setupWebAppContext(contexts);
        setupRestApiContextHandler(webapp);
          

        try {
            jettyWebServer.start();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    private static Server setupJettyServer() {
        final Server server = new Server();
        ServerConnector connector = new ServerConnector(server);

        connector.setHost("0.0.0.0");
        connector.setPort(13800);
        connector.setIdleTimeout(30 * 1000);

        server.addConnector(connector);
        return server;
    }

    private static WebAppContext setupWebAppContext(ContextHandlerCollection contexts) {
        WebAppContext webApp = new WebAppContext();

        webApp.setContextPath("/");
        webApp.setResourceBase("/");
        webApp.setParentLoaderPriority(true);

        webApp.addServlet(new ServletHolder(new DefaultServlet()), "/*");
        contexts.addHandler(webApp);

        webApp.setInitParameter("org.eclipse.jetty.servlet.Default.dirAllowed", "false");

        contexts.addHandler(webApp);
        return webApp;
    }

    private static void setupRestApiContextHandler(WebAppContext webapp) {
        final ServletHolder servletHolder = new ServletHolder(new org.glassfish.jersey.servlet.ServletContainer());

        servletHolder.setInitParameter("javax.ws.rs.Application", App.class.getName());
        servletHolder.setName("rest");
        servletHolder.setForcedPath("rest");

        webapp.setSessionHandler(new SessionHandler());
        webapp.addServlet(servletHolder, "/api/*");
    }
}
