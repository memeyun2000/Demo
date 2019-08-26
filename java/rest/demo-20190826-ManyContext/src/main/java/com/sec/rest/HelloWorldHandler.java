package com.sec.rest;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class HelloWorldHandler extends AbstractHandler {


    private final String helloStr;


    public HelloWorldHandler(){
        this.helloStr = "not init";
    }

    public HelloWorldHandler(String helloStr) {
        this.helloStr = helloStr;
    }

    public void handle(String s, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {

        PrintWriter out = null;

        out = httpServletResponse.getWriter();
        out.println("<h1>" + this.helloStr + "</h1>");


        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        httpServletResponse.setContentType("text/html;charset:utf-8");
        request.setHandled(true);
    }
}
