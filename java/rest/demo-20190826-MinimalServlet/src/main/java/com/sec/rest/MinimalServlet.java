package com.sec.rest;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MinimalServlet {
    public static void main(String args[]) throws Exception{
        Server server = new Server(8080);


        ServletHandler servletHandler = new ServletHandler();
        server.setHandler(servletHandler);


        servletHandler.addServletWithMapping(HelloWorldServlet.class,"/*");

        server.start();
        System.out.println("not join");
        server.join();
        System.out.println(("It's join"));

    }




    public static class HelloWorldServlet extends HttpServlet {

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

            resp.setContentType("text/html;charset:utf-8");
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().println("<h1> hello world minimal servlet</h1>");
        }
    }
}
