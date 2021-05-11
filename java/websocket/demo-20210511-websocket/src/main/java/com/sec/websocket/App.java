package com.sec.websocket;

import com.sec.websocket.server.MyWebSocketServer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.api.ServiceLocatorFactory;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.servlet.ServletProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.ws.rs.core.Application;

/**
 * Hello world!
 *
 */
public class App extends Application {
    private static Logger LOG = LoggerFactory.getLogger(App.class);
    private static Server jettyWebServer;
    public static ServiceLocator sharedServiceLocator;

    public static void main(String[] args) {
        System.out.println("init server!");

        jettyWebServer = setupJettyServer();

        ContextHandlerCollection contexts = new ContextHandlerCollection();
        jettyWebServer.setHandler(contexts);
        WebAppContext webapp = setupWebAppContext(contexts);




        //Inject
        sharedServiceLocator = ServiceLocatorFactory.getInstance().create("shared-locator");
        ServiceLocatorUtilities.enableImmediateScope(sharedServiceLocator);
        ServiceLocatorUtilities.bind(
                sharedServiceLocator,
                new AbstractBinder() {
                    @Override
                    protected void configure() {
                        bindAsContract(MyWebSocketServer.class)
                                .to(WebSocketServlet.class)
                                .in(Singleton.class);
                    }
                });

        //setup websocket
        setupWebSocketContextHandler(webapp,sharedServiceLocator);

        webapp.addEventListener(
                new ServletContextListener() {
                    @Override
                    public void contextInitialized(ServletContextEvent servletContextEvent) {
                        servletContextEvent
                                .getServletContext()
                                .setAttribute(ServletProperties.SERVICE_LOCATOR, sharedServiceLocator);
                    }

                    @Override
                    public void contextDestroyed(ServletContextEvent servletContextEvent) {}
                });



        try {
            jettyWebServer.start();
            jettyWebServer.join();
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

        //允许访问服务器目录
        webApp.setInitParameter("org.eclipse.jetty.servlet.Default.dirAllowed", "true");

        contexts.addHandler(webApp);
        return webApp;
    }

    private static void setupWebSocketContextHandler(WebAppContext webapp,ServiceLocator serviceLocator) {
        final ServletHolder servletHolder = new ServletHolder(serviceLocator.getService(MyWebSocketServer.class));

        servletHolder.setInitParameter("maxTextMessageSize", "10240000");
        webapp.addServlet(servletHolder, "/ws/*");
    }
}
