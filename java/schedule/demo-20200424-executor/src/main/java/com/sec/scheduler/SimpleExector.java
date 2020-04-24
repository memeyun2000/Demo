package com.sec.scheduler;


import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;

/**
 * Hello world!
 *
 */
public class SimpleExector
{
    public static void main( String[] args ) throws Exception {

        String command = "ping www.baidu.com";
        final CommandLine commandLine = CommandLine.parse(command);
        DefaultExecutor executor = new DefaultExecutor();
        int exitValue = executor.execute(commandLine);


        System.out.println("hello world");
    }
}
