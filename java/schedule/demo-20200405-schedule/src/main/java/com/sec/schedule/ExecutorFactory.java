package com.sec.schedule;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorFactory {
    private static ExecutorFactory _exector;
    private static Long _exectorLock =new Long(0);

    Map<String, ExecutorService> executor = new HashMap<String,ExecutorService>();

    private ExecutorFactory () {

    }

    public static ExecutorFactory singleton() {
        if(_exector == null) {
            synchronized (_exectorLock) {
                if(_exector == null) {
                    _exector = new ExecutorFactory();
                }
            }
        }
        return _exector;
    }


    public ExecutorService createOrGet(String name,int numThread) {
        synchronized (executor) {
            if(!executor.containsKey(name)) {
                executor.put(name, Executors.newScheduledThreadPool(numThread));
            }
        }
        return executor.get(name);
    }

    public ExecutorService createOrGet(String name) {
        return createOrGet(name,100);
    }

    public ExecutorService getDefaultExecutor() {
        return createOrGet("default");
    }


    public void shutdown(String name) {
        synchronized (executor) {
            if(executor.containsKey(name)) {
                executor.get(name).shutdown();
                executor.remove(name);
            }
        }
    }


    public void shutdownAll() {
        synchronized (executor) {
            for(String name : executor.keySet()) {
                shutdown(name);
            }
        }
    }
}
