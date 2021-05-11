package com.sec.websocket.server;

import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;

public class MyWebSocketCreator implements WebSocketCreator {

//    private MyWebSocketServer myWebSocketServer;
//
//
//    public MyWebSocketCreator(MyWebSocketServer server) {
//        this.myWebSocketServer = server;
//    }

    @Override
    public Object createWebSocket(ServletUpgradeRequest request, ServletUpgradeResponse response) {
        return new MyWebSocket(request.getHttpServletRequest(),"");
    }
}
