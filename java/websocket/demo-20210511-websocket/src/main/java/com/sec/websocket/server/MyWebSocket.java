package com.sec.websocket.server;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;

import javax.servlet.http.HttpServletRequest;

public class MyWebSocket extends WebSocketAdapter {
    private String username;
    private Session connection;
    private HttpServletRequest request;
    private String protocol;

    public MyWebSocket(HttpServletRequest request,String protocol) {
        this.request = request;
        this.protocol = protocol;
        this.username = "";
    }


    @Override
    public void onWebSocketText(String message) {
        System.out.println("server: message is:" + message);
    }

    @Override
    public void onWebSocketClose(int statusCode, String reason) {
        System.out.println("server: connection close");
    }

    @Override
    public void onWebSocketConnect(Session connection) {
        System.out.println("server: connection open");
        this.connection = connection;
    }
}
