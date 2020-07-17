package com.sec.zeppelin;

import org.apache.zeppelin.conf.ZeppelinConfiguration;
import org.apache.zeppelin.notebook.NoteInterpreterLoader;
import org.apache.zeppelin.notebook.repo.VFSNotebookRepo;

public class Demo20200717Conf {
    public static void main(String args[]) {
        ZeppelinConfiguration conf = ZeppelinConfiguration.create();

        System.out.println(conf.getString(ZeppelinConfiguration.ConfVars.ZEPPELIN_PORT));
    }



}
