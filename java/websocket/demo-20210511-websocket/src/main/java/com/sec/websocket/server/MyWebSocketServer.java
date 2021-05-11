package com.sec.websocket.server;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

public class MyWebSocketServer extends WebSocketServlet {
    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.setCreator(new MyWebSocketCreator());
    }
}
