package com.sec.sync;

import org.omg.PortableServer.THREAD_POLICY_ID;

/**
 * Hello world!
 *
 */
public class App extends Thread
{
    public static void main( String[] args )
    {
        Thread thread = new App();
        thread.start();
        System.out.println("thread 1 start");
        Thread thread1 = new App();
        thread1.start();
        System.out.println("thread 2 start");
    }

    public static Long lock = new Long(0);
    @Override
    public void run() {

        try{
            synchronized (lock) {
                Thread.sleep(5000);
                System.out.println("Hello");
            }
        } catch (Exception e) {

        }
    }
}
