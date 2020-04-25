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
        Map<String,String> data = new HashMap<String,String>();
        data.put("password","123445566");
        data.put("username","guoqingyun");
        data.put("realname","memeyun");
        MyMap map = new MyMap(data);
        Map<String,String> result = map.filter(new MyMap.FilterPredicate() {
            @Override
            public boolean apply(String key) {
                return !key.equals("password");
            }
        });


        Iterator<String> iter = result.keySet().iterator();
        while(iter.hasNext()) {
            String key = iter.next();
            String value = result.get(key);

            System.out.println("key:" + key + ",value:" + value);
        }
    }
}
