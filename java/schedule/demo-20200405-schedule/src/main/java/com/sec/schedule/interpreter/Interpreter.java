package com.sec.schedule.interpreter;

public class Interpreter {
    private volatile boolean isOpened = false;




    public void createOrGetProcess() throws Exception{
        synchronized (this) {
            if(!isOpened) {
                System.out.println("open on process");
                open();
            }
        }

    }


    public void open() throws Exception{
       synchronized (this) {
           Thread.sleep(5000);
           isOpened = true;
           System.out.println("process is open");
       }
    }
}
