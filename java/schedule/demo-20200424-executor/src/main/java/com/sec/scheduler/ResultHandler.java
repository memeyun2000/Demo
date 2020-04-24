package com.sec.scheduler;


import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;

public class ResultHandler {
    public static void main(String args[]) throws Exception{
        final CommandLine commandLine = CommandLine.parse("ping www.baidu.com");
        final DefaultExecuteResultHandler handler = new DefaultExecuteResultHandler();

        DefaultExecutor executor = new DefaultExecutor();
        executor.execute(commandLine,handler);

        System.out.println("hello world1");
        handler.waitFor(5000);
//        Thread.sleep(5000);
        System.out.println("hello world2");
    }
}
