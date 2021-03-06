package com.sec.rest;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.ws.rs.core.Application;

import com.sec.rest.rest.AppRestApi;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.rmi.runtime.Log;

/**
 * Hello world!
 * curl -X GET "http://localhost:13800/api/info"
 */
public class App extends Application{
    private static Logger LOG = LoggerFactory.getLogger(App.class);
    private static Server jettyWebServer;

    public static void main(String[] args) {

        jettyWebServer = setupJettyServer();

        ContextHandlerCollection contexts = new ContextHandlerCollection();
        jettyWebServer.setHandler(contexts);
        WebAppContext webapp = setupWebAppContext(contexts);
        setupRestApiContextHandler(webapp);
          

        try {
            LOG.info("server start ...");
            jettyWebServer.start();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }



        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                LOG.info("shutdown server ...");

                try{
                    jettyWebServer.stop();
                } catch (Exception e) {
                    LOG.error("Error in stopping server");
                }
            }
        });
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

        // 设置context path 即工程名称
        webApp.setContextPath("/project");
        // 设置其实静态资源目录
        webApp.setResourceBase("/demo/java");
        webApp.setParentLoaderPriority(true);

        webApp.addServlet(new ServletHolder(new DefaultServlet()), "/*");
//        contexts.addHandler(webApp);

        //允许访问服务器目录
        webApp.setInitParameter("org.eclipse.jetty.servlet.Default.dirAllowed", "true");

        contexts.addHandler(webApp);
        return webApp;
    }

    private static void setupRestApiContextHandler(WebAppContext webapp) {
        final ServletHolder servletHolder = new ServletHolder(new org.glassfish.jersey.servlet.ServletContainer());

        servletHolder.setInitParameter("javax.ws.rs.Application", App.class.getName());
        servletHolder.setInitParameter("jersey.config.server.provider.packages", "com.sec.rest.rest");
        servletHolder.setName("rest");


        webapp.setSessionHandler(new SessionHandler());
        webapp.addServlet(servletHolder, "/api/*");
    }
}
