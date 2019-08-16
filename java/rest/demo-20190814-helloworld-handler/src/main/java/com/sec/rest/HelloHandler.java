package com.sec.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import java.io.PrintWriter;

public class HelloHandler extends AbstractHandler {
    private final String greeting;
    private final String body;

    public HelloHandler() {
        this("hello worlds",null);
    }

    public HelloHandler(String greeting,String body) {
        this.body = body;
        this.greeting = greeting;
    }





    @Override
    public void handle(String target,Request baseRequest,HttpServletRequest request,HttpServletResponse response) {


        PrintWriter out = null;

        try {
            out = response.getWriter();
            out.println("<h1>" + greeting + "</h1>");
            if (body!= null) {
                out.println(body);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }





        response.setContentType("text/html;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);
    }





    //getter and setting
    public String getGreeting() {
        return greeting;
    }

    public String getBody() {
        return body;
    }
}
