package com.sec.rest;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.BaseHolder;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ListenerHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;

import javax.servlet.*;
import java.io.IOException;
import java.util.EnumSet;

public class OneServletContext {

    public static void main(String args[]) throws Exception {
        Server server = new Server(8080);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        context.setResourceBase(System.getProperty("java.io.tmpdir"));
        server.setHandler(context);


        context.addServlet(context.addServlet(DumpServlet.class, "/dump/*"), "*.dump");
        context.addServlet(DefaultServlet.class, "/");

        context.addFilter(TestFilter.class, "/*", EnumSet.of(DispatcherType.REQUEST));
        context.addFilter(TestFilter.class, "/test", EnumSet.of(DispatcherType.REQUEST, DispatcherType.ASYNC));
        context.addFilter(TestFilter.class, "*.test", EnumSet.of(DispatcherType.REQUEST, DispatcherType.INCLUDE, DispatcherType.FORWARD));



        server.start();
        server.dumpStdErr();
        server.join();
    }


    public static class TestFilter implements Filter {
        public void init(FilterConfig filterConfig) throws ServletException {

        }

        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
            System.out.println("hello world filter");
            chain.doFilter(request, response);
        }

        public void destroy() {

        }
    }

}
