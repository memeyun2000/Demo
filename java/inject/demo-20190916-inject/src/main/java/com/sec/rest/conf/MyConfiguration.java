package com.sec.rest.conf;

import javax.inject.Inject;

public class MyConfiguration {
    @Inject
    public static MyConfiguration conf;

    public MyConfiguration(){
        System.out.println("contract MyConfiguration");
    }
    public static synchronized MyConfiguration create() {

        if (conf != null) {
            return conf;
        }
        System.out.println("call create conf method()");
        return new MyConfiguration();
    }


    public void getConfig(){
        System.out.println("called method getConfig");
    }
}
