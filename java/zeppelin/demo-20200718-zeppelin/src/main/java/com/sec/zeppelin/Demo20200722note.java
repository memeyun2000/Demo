package com.sec.zeppelin;

import org.apache.zeppelin.conf.ZeppelinConfiguration;
import org.apache.zeppelin.dep.DependencyResolver;
import org.apache.zeppelin.interpreter.InterpreterFactory;
import org.apache.zeppelin.notebook.Note;
import org.apache.zeppelin.notebook.Notebook;
import org.apache.zeppelin.notebook.repo.NotebookRepo;
import org.apache.zeppelin.notebook.repo.NotebookRepoSync;

import java.util.List;

public class Demo20200722note {
    public static void main(String args[]) throws Exception{
        ZeppelinConfiguration conf = ZeppelinConfiguration.create();
        String localRepoPath = conf.getString(ZeppelinConfiguration.ConfVars.ZEPPELIN_DEP_LOCALREPO);

        DependencyResolver resolver = new DependencyResolver(localRepoPath);
        InterpreterFactory replFactory = new InterpreterFactory(conf,null,null,resolver);
        NotebookRepo repo = new NotebookRepoSync(conf);
        Notebook notebook = new Notebook(conf,repo,null,replFactory,null,null,null,null);


        List<Note> notes = notebook.getAllNotes();


        System.out.println("hello world");
    }
}
