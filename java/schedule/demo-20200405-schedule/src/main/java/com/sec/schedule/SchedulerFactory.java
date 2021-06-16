package com.sec.schedule;

import com.sec.schedule.job.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

public class SchedulerFactory implements SchedulerListener{
    public static Logger LOG = LoggerFactory.getLogger(SchedulerFactory.class);

    private ExecutorService executor;
    private Map<String,Scheduler> schedulers = new LinkedHashMap<String,Scheduler>();

    private static SchedulerFactory singleton;
    private static Long singleLock = new Long(0);



    public static SchedulerFactory singleton () {
        if(singleton == null) {
            synchronized (singleLock) {
                if(singleton ==null) {
                    singleton = new SchedulerFactory();
                }
            }
        }
        return singleton;
    }

    private SchedulerFactory () {
        executor = ExecutorFactory.singleton().createOrGet("schedulerFactory",100);
    }

    public void destory() {
        ExecutorFactory.singleton().shutdown("schedulerFactory");
    }


    public Scheduler createOrGetFIFOScheduler(String name) {
        synchronized (schedulers) {
            if (schedulers.containsKey(name) == false) {
                Scheduler s = new FIFOScheduler(name, executor, this);
                schedulers.put(name, s);
                executor.execute(s);
            }
            return schedulers.get(name);
        }
    }

    public Scheduler createOrGetParallelScheduler(String name,int maxConcurrency) {
       synchronized (schedulers) {
           if (schedulers.containsKey(name) == false) {
               Scheduler s = new ParallelScheduler(name, executor ,this ,maxConcurrency);
               schedulers.put(name, s);
               executor.execute(s);
           }
           return schedulers.get(name);
       }
    }

    @Override
    public void jobStarted(Scheduler scheduler, Job job) {
        LOG.info("Job " + job.getJobName() + " started by scheduler " + scheduler.getName());
    }

    @Override
    public void jobFinished(Scheduler scheduler, Job job) {
        LOG.info("Job " + job.getJobName() + " finished by scheduler " + scheduler.getName());
    }
}
