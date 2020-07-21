package com.sec.apply;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        System.out.println("hell world");
        MyHashMap hashMap = new MyHashMap();
        hashMap.put("hello","world");
        hashMap.setIds("myid");
        System.out.println(hashMap.getIds());

        MyNoExtendsHashMap noExtendsHashMap = new MyNoExtendsHashMap();
        noExtendsHashMap.setIds("id");

        System.out.println("ai");

    }
}
