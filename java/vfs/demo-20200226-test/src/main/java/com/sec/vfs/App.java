package com.sec.vfs;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.VFS;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception {
        System.out.println("Hello World!" );
        FileSystemManager fsm = VFS.getManager();
        FileObject fileObject = fsm.resolveFile("file://c:/Users/cmbc3/demo/Demo/asset/temp.txt");

        if(fileObject.exists()) {
            fileObject.delete();
        } else {
            fileObject.createFile();
        }
    }
}
