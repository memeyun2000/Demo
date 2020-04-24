package com.sec.scheduler;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;

public class WatchDog {
    public static void main(String args[]) throws Exception {
        final CommandLine commandLine = CommandLine.parse("ping www.baidu.com");
        final ExecuteWatchdog watchdog = new ExecuteWatchdog(Integer.MAX_VALUE);
        final DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
        DefaultExecutor executor = new DefaultExecutor();

        executor.setWatchdog(watchdog);
        executor.execute(commandLine, resultHandler);

        Thread.sleep(5000);//等进程执行一会，再终止它
        System.out.println("--> Watchdog is watching ? " + watchdog.isWatching());
        watchdog.destroyProcess();//终止进程
        //guoqy: 注意：这里如果不等待的话,会导致线程未终止所以取不到退出状态也拿不到报错信息
        resultHandler.waitFor();//等待5秒。下面加上上面的几个System.out，看看进程状态是什么。
//        Thread.sleep(5000);//等进程执行一会，再终止它
        System.out.println("--> destroyProcess done.");
        System.out.println("--> Watchdog is watching ? " + watchdog.isWatching());
        System.out.println("--> Watchdog should have killed the process : " + watchdog.killedProcess());
        System.out.println("--> wait result is : " + resultHandler.hasResult());
        System.out.println("--> exit value is : " + resultHandler.getExitValue());
        System.out.println("--> exception is : " + resultHandler.getException());
    }
}
