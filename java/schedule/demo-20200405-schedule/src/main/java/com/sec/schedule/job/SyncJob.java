package com.sec.schedule.job;

import com.sec.schedule.interpreter.Interpreter;
import com.sec.schedule.interpreter.InterpreterFactory;

import java.util.Map;
import java.util.Random;

public class SyncJob extends Job {
    private Interpreter interpreter;

    public SyncJob(){
        super(generateId(),null);
    }
    public SyncJob(String name){
        super(generateId() + name,null);
    }
    @Override
    protected Object runJob() throws Throwable {
        interpreter = InterpreterFactory.getInterpreterDefault();
        interpreter.createOrGetProcess();
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
