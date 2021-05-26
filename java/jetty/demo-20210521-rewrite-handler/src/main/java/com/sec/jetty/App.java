package com.sec.jetty;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.rewrite.handler.CompactPathRule;
import org.eclipse.jetty.rewrite.handler.RedirectRegexRule;
import org.eclipse.jetty.rewrite.handler.RewriteHandler;
import org.eclipse.jetty.rewrite.handler.RewriteRegexRule;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;

import java.io.IOException;

/**
 * Hello world!
 * Server
 * └── RewriteHandler
 *     └── ContextHandlerCollection
 *         ├── ContextHandler 1
 *         :── ...
 *         └── ContextHandler N
 *
 *
 * 如果调用http://localhost:8080/doc/xx
 * 只会执行redirect操作，不会执行contextHandler
 */
public class App 
{

    public static void main( String[] args ) throws Exception {
        class DocHandler extends AbstractHandler {

            public void handle(String s, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
                request.setHandled(true);
                System.out.println("do doc handler");
            }
        }

        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8080);
        server.addConnector(connector);

        RewriteHandler rewriteHandler = new RewriteHandler();
        rewriteHandler.addRule(new CompactPathRule());
        rewriteHandler.addRule(new RewriteRegexRule("/(.*)/product/(.*)", "/$1/p/$2"));

        RedirectRegexRule redirectRule = new RedirectRegexRule("/doc/(.*)", "https://www.baidu.com");
        redirectRule.setStatusCode(HttpStatus.MOVED_PERMANENTLY_301);
        rewriteHandler.addRule(redirectRule);

        server.setHandler(rewriteHandler);
        ContextHandlerCollection contexts = new ContextHandlerCollection();
        rewriteHandler.setHandler(contexts);

        ContextHandler context = new ContextHandler();
        context.setHandler(new DocHandler());
        context.setContextPath("/doc");
        contexts.addHandler(context);
        
        server.start();
    }
}
