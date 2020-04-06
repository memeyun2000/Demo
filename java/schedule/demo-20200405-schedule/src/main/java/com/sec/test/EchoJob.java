package com.sec.test;

import com.sec.schedule.Job;

import java.util.Map;
import java.util.Random;

public class EchoJob extends Job {
    public EchoJob(){
        super(generateId(),null);
    }
    public EchoJob(String name){
        super(generateId() + name,null);
    }
    @Override
    protected Object runJob() throws Throwable {
        System.out.println("this is "+this.getJobName());
        Thread.sleep(1000);
        return this.getJobName();
    }

    @Override
    public int progress() {
        return 0;
    }

    @Override
    public Map<String, Object> info() {
        return null;
    }

    @Override
    protected boolean jobAbort() {
        return false;
    }
    private static String generateId() {
        return "paragraph_" + System.currentTimeMillis() + "_"
                + new Random(System.currentTimeMillis()).nextInt();
    }
}
