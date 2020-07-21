package com.sec.zeppelin;

import org.apache.zeppelin.conf.ZeppelinConfiguration;
import org.apache.zeppelin.notebook.Notebook;
import org.apache.zeppelin.notebook.repo.NotebookRepo;
import org.apache.zeppelin.notebook.repo.NotebookRepoSync;
import org.apache.zeppelin.socket.NotebookServer;

public class Demo20200721NotebookRepo {
    public static void main(String args[]) throws Exception{
        ZeppelinConfiguration conf = ZeppelinConfiguration.create();
        NotebookRepo notebookRepo = new NotebookRepoSync(conf);

        System.out.println("hello world");
    }
}
