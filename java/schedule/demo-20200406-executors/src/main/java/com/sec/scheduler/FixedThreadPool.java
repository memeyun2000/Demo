package com.sec.scheduler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FixedThreadPool {
    public static void main(String args[]) {
        ExecutorService executor = Executors.newFixedThreadPool(3);

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
