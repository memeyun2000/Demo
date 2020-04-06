package com.sec.scheduler;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * 注意，这里只放入了一个任务
 * 定频率执行
 * 和Rate不同的是：Delay是需要等待上一个任务执行完，再过delay时间，放入执行任务
 */
public class ScheduledFixedDelayThreadPool {
    public static void main(String args[]) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);

        executor.scheduleWithFixedDelay(new Runnable() {
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
