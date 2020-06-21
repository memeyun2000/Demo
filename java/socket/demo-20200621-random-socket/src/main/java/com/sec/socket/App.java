package com.sec.socket;

import java.net.ServerSocket;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception {
        System.out.println( App.findRandomAvailablePortOnAllLocalInterfaces());
    }


    public static int findRandomAvailablePortOnAllLocalInterfaces() throws Exception {
        ServerSocket socket = new ServerSocket(0);
        int port = socket.getLocalPort();
        socket.close();

        return port;
    }
}
