package com.sec.rest.websocket;

import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;

public class MyAdvancedEchoCreator implements WebSocketCreator {
    AnnotatedEchoSocket annotatedEchoSocket;

    public MyAdvancedEchoCreator() {
        annotatedEchoSocket = new AnnotatedEchoSocket();
    }

    public Object createWebSocket(ServletUpgradeRequest servletUpgradeRequest, ServletUpgradeResponse servletUpgradeResponse) {
        for (String sub : servletUpgradeRequest.getSubProtocols()) { /**
         *   官方的Demo，这里可以根据相应的参数做判断，使用什么样的websocket
         */} // 没有有效的请求，忽略它
        // 没有有效的请求，忽略它
        return annotatedEchoSocket;
    }
}
