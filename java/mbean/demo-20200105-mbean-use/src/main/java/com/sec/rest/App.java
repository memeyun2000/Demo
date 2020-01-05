package com.sec.rest;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.ws.rs.core.Application;

import com.sec.rest.rest.AppRestApi;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 * curl -X GET "http://localhost:13800/api/info"
 */
public class App extends Application {
    private static Logger LOG = LoggerFactory.getLogger(App.class);
    private static Server jettyWebServer;

    public static void main(String[] args) throws Exception{
        System.out.println("Hello World!");

        jettyWebServer = setupJettyServer();

        ContextHandlerCollection contexts = new ContextHandlerCollection();
        jettyWebServer.setHandler(contexts);
        WebAppContext webapp = setupWebAppContext(contexts);
        setupRestApiContextHandler(webapp);


        /**
         * 1. MBean接口  受监控的类是AppRestApi 则MBean名称为 AppRestApiMbean
         * 2. 注册MBean 如下
         */
        MBeanServer server2 = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("com.sec.rest.rest:name=appRestApi");
        server2.registerMBean(new AppRestApi(), name);
        try {
            jettyWebServer.start();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    private static Server setupJettyServer() throws Exception{
        final Server server = new Server();
        ServerConnector connector = new ServerConnector(server);

        connector.setHost("0.0.0.0");
        connector.setPort(13800);
        connector.setIdleTimeout(30 * 1000);

        server.addConnector(connector);


        return server;
    }

    private static WebAppContext setupWebAppContext(ContextHandlerCollection contexts){
        WebAppContext webApp = new WebAppContext();

        webApp.setContextPath("/");
        webApp.setResourceBase("/");
        webApp.setParentLoaderPriority(true);

        webApp.addServlet(new ServletHolder(new DefaultServlet()), "/*");
        contexts.addHandler(webApp);

        //允许访问服务器目录
        webApp.setInitParameter("org.eclipse.jetty.servlet.Default.dirAllowed", "true");

        contexts.addHandler(webApp);



        return webApp;
    }

    private static void setupRestApiContextHandler(WebAppContext webapp) throws Exception{
        final ServletHolder servletHolder = new ServletHolder(new org.glassfish.jersey.servlet.ServletContainer());

        servletHolder.setInitParameter("javax.ws.rs.Application", App.class.getName());
        servletHolder.setInitParameter("jersey.config.server.provider.packages", "com.sec.rest.rest");
        servletHolder.setName("rest");


        webapp.setSessionHandler(new SessionHandler());
        webapp.addServlet(servletHolder, "/api/*");

    }
}
