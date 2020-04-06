package com.sec.scheduler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPool {
    public static void main(String args[]) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);

        for (int i = 0; i < 100; i++) {
            final int index = i;
            executor.schedule(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                        System.out.println(index);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            },1, TimeUnit.SECONDS);
        }



    }
}
