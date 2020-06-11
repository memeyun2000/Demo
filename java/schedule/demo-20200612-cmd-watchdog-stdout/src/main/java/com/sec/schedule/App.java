package com.sec.schedule;

import org.apache.commons.exec.*;

import java.io.ByteArrayOutputStream;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception {
//        final CommandLine command = CommandLine.parse("ping -t www.baidu.com");
        final CommandLine command = CommandLine.parse("ping www.baidu.com");

        ByteArrayOutputStream stdStream = new ByteArrayOutputStream();
        ByteArrayOutputStream errStream = new ByteArrayOutputStream();

        final ExecuteWatchdog watchdog = new ExecuteWatchdog(Integer.MAX_VALUE);
        final DefaultExecuteResultHandler handler = new DefaultExecuteResultHandler();

        DefaultExecutor executor = new DefaultExecutor();
        executor.setStreamHandler(new PumpStreamHandler(stdStream,errStream));

        try {
            executor.setWatchdog(watchdog);
            executor.execute(command,handler);
        } catch (Exception e) {
        }
        Thread.sleep(5000);

        watchdog.destroyProcess();
        handler.waitFor();

        System.out.println("std error: " + errStream.toString() + " over");
        System.out.println("stdout: " + stdStream.toString() + " over");

        System.out.println("--> destroyProcess done.");
        System.out.println("--> Watchdog is watching ? " + watchdog.isWatching());
        System.out.println("--> Watchdog should have killed the process : " + watchdog.killedProcess());
        System.out.println("--> wait result is : " + handler.hasResult());
        System.out.println("--> exit value is : " + handler.getExitValue());
        System.out.println("--> exception is : " + handler.getException());
    }
}
