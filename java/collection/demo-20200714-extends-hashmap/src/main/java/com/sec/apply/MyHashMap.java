package com.sec.apply;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class MyHashMap extends ConcurrentHashMap<String,String> {
    private String ids ;

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }
}
