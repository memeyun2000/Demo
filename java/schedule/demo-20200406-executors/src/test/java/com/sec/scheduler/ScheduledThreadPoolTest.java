package com.sec.scheduler;

import org.junit.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolTest {
    @Test
    public void schedule() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);

        for (int i = 0; i < 100; i++) {
            final int index = i;
            executor.schedule(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(index);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            },1, TimeUnit.SECONDS);
        }

    }
}
