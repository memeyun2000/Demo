package com.sec.zeppelin;

import org.apache.zeppelin.conf.ZeppelinConfiguration;
import org.apache.zeppelin.dep.DependencyResolver;

public class Demo20200717DepResolve {
    public static void main(String args[]) {
        ZeppelinConfiguration conf = ZeppelinConfiguration.create();
        DependencyResolver depResolver;
        depResolver = new DependencyResolver(
                conf.getString(ZeppelinConfiguration.ConfVars.ZEPPELIN_INTERPRETER_LOCALREPO));
    }
}
