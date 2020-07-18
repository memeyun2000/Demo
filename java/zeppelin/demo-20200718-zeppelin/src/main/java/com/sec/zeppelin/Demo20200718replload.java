package com.sec.zeppelin;

import org.apache.zeppelin.conf.ZeppelinConfiguration;
import org.apache.zeppelin.dep.DependencyResolver;
import org.apache.zeppelin.interpreter.InterpreterFactory;
import org.apache.zeppelin.notebook.NoteInterpreterLoader;

public class Demo20200718replload {
    public static void main(String args[]) throws Exception{
        ZeppelinConfiguration conf = ZeppelinConfiguration.create();
        String localRepoPath = conf.getString(ZeppelinConfiguration.ConfVars.ZEPPELIN_DEP_LOCALREPO);

        DependencyResolver resolver = new DependencyResolver(localRepoPath);
        InterpreterFactory replFactory = new InterpreterFactory(conf,null,null,resolver);
        NoteInterpreterLoader loader = new NoteInterpreterLoader(replFactory);



        System.out.println("hello world");
    }
}
