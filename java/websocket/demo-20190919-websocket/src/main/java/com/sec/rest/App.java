package com.sec.rest;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;

import com.sec.rest.conf.MyConfiguration;
import com.sec.rest.rest.AppRestApi;
import com.sec.rest.websocket.WebSocketHandlerTest;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.api.ServiceLocatorFactory;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 1. 启动应用初始化 MyConfiguration 单例
 * 2. 访问如下url
 *
 * curl -X GET "http://localhost:13800/api/info"
 * curl -X GET "http://localhost:13800/api/conf"
 * 可以看到 使用的是 MyConfiguration 的注入对象
 *
 */
public class App extends Application{
    private static Logger LOG = LoggerFactory.getLogger(App.class);
    private static Server jettyWebServer;
    public static ServiceLocator sharedServiceLocator;

    public static void main(String[] args) {
        System.out.println("init server!");

        jettyWebServer = setupJettyServer();

        ContextHandlerCollection contexts = new ContextHandlerCollection();
        jettyWebServer.setHandler(contexts);
        WebAppContext webapp = setupWebAppContext(contexts);
        setupRestApiContextHandler(webapp);

        //添加 WebSocket
        setupWebSocketContextHandler(contexts);



        //Inject
        //构造 conf 实例对象 并绑定 {
        final MyConfiguration conf = MyConfiguration.create();

        sharedServiceLocator = ServiceLocatorFactory.getInstance().create("shared-locator");
        ServiceLocatorUtilities.enableImmediateScope(sharedServiceLocator);
        ServiceLocatorUtilities.bind(
                sharedServiceLocator,
                new AbstractBinder() {
                    @Override
                    protected void configure() {

                        bind(conf).to(MyConfiguration.class);
                    }
                });

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

        // }


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

    private static void setupWebSocketContextHandler(ContextHandlerCollection contexts) {

        WebSocketHandlerTest test = new WebSocketHandlerTest();
        ContextHandler context = new ContextHandler();
        /* 路径 */
        context.setContextPath("/test");
        context.setHandler(test);


        contexts.addHandler(context);
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

    private static void setupRestApiContextHandler(WebAppContext webapp) {
        final ServletHolder servletHolder = new ServletHolder(new org.glassfish.jersey.servlet.ServletContainer());

        servletHolder.setInitParameter("javax.ws.rs.Application", App.class.getName());
        servletHolder.setInitParameter("jersey.config.server.provider.packages", "com.sec.rest.rest");
        servletHolder.setName("rest");


        webapp.setSessionHandler(new SessionHandler());
        webapp.addServlet(servletHolder, "/api/*");
    }
}
