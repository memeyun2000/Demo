package com.sec.jetty;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.StatisticsHandler;

import java.io.IOException;

public class MyStatisticHandler extends AbstractHandler {
    public void handle(String s, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        request.setHandled(true);
        StatisticsHandler statistic = App.server.getChildHandlerByClass(StatisticsHandler.class);
        System.out.println("time max:" + statistic.getRequestTimeMax());
        System.out.println("request time total:" + statistic.getRequestTimeTotal());
        System.out.println("request count:" + statistic.getRequests());


    }
}
