package com.sec.scheduler;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * 注意，这里只放入了一个任务
 * 定频率执行
 */
public class ScheduledFixedRateThreadPool {
    public static void main(String args[]) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(6000);
                    System.out.println(new Date());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 1, 3, TimeUnit.SECONDS);


    }
}
