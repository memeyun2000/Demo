package com.sec.zeppelin;

import org.apache.zeppelin.conf.ZeppelinConfiguration;

public class Demo20200718Conf {
    public static void main(String args[]) {
        ZeppelinConfiguration conf = ZeppelinConfiguration.create();

        System.out.println(conf.getString(ZeppelinConfiguration.ConfVars.ZEPPELIN_PORT));


    }
}
