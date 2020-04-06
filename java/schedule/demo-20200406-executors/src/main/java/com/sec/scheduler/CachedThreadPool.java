package com.sec.scheduler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CachedThreadPool {
    public static void main(String args[]) {
        ExecutorService executor = Executors.newCachedThreadPool();

        for (int i = 0; i < 100; i++) {
            final int index = i;
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(index * 1000);
                        System.out.println(index);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

    }
}
