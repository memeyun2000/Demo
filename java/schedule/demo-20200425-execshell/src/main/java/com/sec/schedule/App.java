package com.sec.schedule;


import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;

import java.io.ByteArrayOutputStream;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception{
        System.out.println( "Hello World!" );
//        String command = "echo 'hello world'";
        String command = "ping -t www.baidu.com";


        ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
        ByteArrayOutputStream stdStream = new ByteArrayOutputStream();
        final DefaultExecuteResultHandler handler = new DefaultExecuteResultHandler();

        CommandLine commandLine = CommandLine.parse(command);
        DefaultExecutor executor = new DefaultExecutor();

        executor.setStreamHandler(new PumpStreamHandler(stdStream,errorStream));
        try {
            executor.execute(commandLine);
        } catch (Exception e) {
            System.out.println("err:" + errorStream.toString());
        }

        System.out.println("std:" + stdStream.toString());
    }
}
