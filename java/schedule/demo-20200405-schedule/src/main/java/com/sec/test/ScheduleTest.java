package com.sec.test;

import com.sec.schedule.Scheduler;
import com.sec.schedule.SchedulerFactory;

/**
 * Hello world!
 *
 */
public class ScheduleTest
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        ScheduleTest app = new ScheduleTest();
//        Scheduler scheduler = app.getFIFOScheduler();
        Scheduler scheduler = app.getParallelScheduler();

        scheduler.submit(new EchoJob("1"));
        scheduler.submit(new EchoJob("2"));
        scheduler.submit(new EchoJob("3"));
        scheduler.submit(new EchoJob("4"));
        scheduler.submit(new EchoJob("5"));
    }


    public Scheduler getFIFOScheduler() {
        return SchedulerFactory.singleton().createOrGetFIFOScheduler("FIFO");
    }
    public Scheduler getParallelScheduler() {
        return SchedulerFactory.singleton().createOrGetParallelScheduler("Parallel",3);
    }
}
