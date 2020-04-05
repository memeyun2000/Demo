package com.sec.schedule;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        App app = new App();
        Scheduler scheduler = app.getScheduler();

        scheduler.submit(new EchoJob("1"));
        scheduler.submit(new EchoJob("2"));
        scheduler.submit(new EchoJob("3"));
        scheduler.submit(new EchoJob("4"));
        scheduler.submit(new EchoJob("5"));
    }


    public Scheduler getScheduler() {
        return SchedulerFactory.singleton().createOrGetFIFOScheduler("interprter");
    }
}
