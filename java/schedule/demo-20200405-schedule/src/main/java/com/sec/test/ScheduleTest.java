package com.sec.test;

import com.sec.schedule.Scheduler;
import com.sec.schedule.SchedulerFactory;
import com.sec.schedule.job.SyncJob;

/**
 * Hello world!
 *
 */
public class ScheduleTest
{
    public static void main( String[] args ) throws Exception
    {
        System.out.println( "Hello World!" );

        ScheduleTest app = new ScheduleTest();
//        Scheduler scheduler = app.getFIFOScheduler();
        Scheduler scheduler = app.getParallelScheduler();

        for(int i =0 ;i <10 ; i++) {
            System.out.println("put one submit,No." + i);
            scheduler.submit(new SyncJob("No." + i));
//            scheduler.submit(new EchoJob("No." + i));
            Thread.sleep(50);
        }
    }


    public Scheduler getFIFOScheduler() {
        return SchedulerFactory.singleton().createOrGetFIFOScheduler("FIFO");
    }
    public Scheduler getParallelScheduler() {
        return SchedulerFactory.singleton().createOrGetParallelScheduler("Parallel",3);
    }
}
