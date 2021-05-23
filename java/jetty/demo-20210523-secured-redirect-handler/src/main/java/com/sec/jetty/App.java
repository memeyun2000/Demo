package com.sec.jetty;

import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.SecuredRedirectHandler;
import org.eclipse.jetty.util.ssl.SslContextFactory;

/**
 * Hello world!
 * 生成keysotre
 * $JAVA_HOME/bin/keytool -genkey -alias localhost -keyalg RSA -validity 365 -keystore jetty.keystore
 */
public class App 
{
    public static void main( String[] args ) throws Exception{

        Server server = new Server();

        int securePort = 8443;
        HttpConfiguration httpConfig = new HttpConfiguration();
        httpConfig.setSecurePort(securePort);

        ServerConnector connector = new ServerConnector(server, new HttpConnectionFactory(httpConfig));
        connector.setPort(8080);
        server.addConnector(connector);

// Configure the HttpConfiguration for the encrypted connector.
        HttpConfiguration httpsConfig = new HttpConfiguration(httpConfig);
// Add the SecureRequestCustomizer because we are using TLS.
        httpConfig.addCustomizer(new SecureRequestCustomizer());

// The HttpConnectionFactory for the encrypted connector.
        HttpConnectionFactory http11 = new HttpConnectionFactory(httpsConfig);

// Configure the SslContextFactory with the keyStore information.
        SslContextFactory.Server sslContextFactory = new SslContextFactory.Server();
        sslContextFactory.setKeyStorePath("jetty.keystore");
        sslContextFactory.setKeyStorePassword("guoqingyun");

// The ConnectionFactory for TLS.
        SslConnectionFactory tls = new SslConnectionFactory(sslContextFactory, http11.getProtocol());

// The encrypted connector.
        ServerConnector secureConnector = new ServerConnector(server, tls, http11);
        secureConnector.setPort(8443);
        server.addConnector(secureConnector);

        SecuredRedirectHandler securedHandler = new SecuredRedirectHandler();

// Link the SecuredRedirectHandler to the Server.
        server.setHandler(securedHandler);

// Create a ContextHandlerCollection to hold contexts.
        ContextHandlerCollection contextCollection = new ContextHandlerCollection();
// Link the ContextHandlerCollection to the StatisticsHandler.
        securedHandler.setHandler(contextCollection);

        server.start();
    }
}
