package com.sec.zeppelin;

import org.apache.zeppelin.conf.ZeppelinConfiguration;
import org.apache.zeppelin.dep.DependencyResolver;
import org.apache.zeppelin.interpreter.InterpreterFactory;
import org.apache.zeppelin.notebook.NoteInterpreterLoader;

public class Demo20200717ReplFactory {

    public static void main(String args[]) throws Exception{
        ZeppelinConfiguration conf = ZeppelinConfiguration.create();
        DependencyResolver depResolver;
        depResolver = new DependencyResolver(conf.getString(ZeppelinConfiguration.ConfVars.ZEPPELIN_INTERPRETER_LOCALREPO));

        InterpreterFactory interpreterFactory = new InterpreterFactory(conf,null,null,depResolver);

        System.out.println("hello world");
    }


}
