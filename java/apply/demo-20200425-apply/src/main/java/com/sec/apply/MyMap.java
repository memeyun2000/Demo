package com.sec.apply;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MyMap {
    Map<String,String> conf;

    public MyMap(Map<String,String> conf) {
        this.conf = conf;
    }

    public Map<String,String> filter(FilterPredicate predicate) {
        Map<String,String> _conf = new HashMap<String,String>();

        Iterator<String> iter = conf.keySet().iterator();
        while (iter.hasNext()) {
            String _key = iter.next();
            String _value = conf.get(_key);

            if(!predicate.apply(_key)) {
                continue;
            }

            _conf.put(_key,_value);
        }

        return _conf;
    }

    interface FilterPredicate{
        public boolean apply(String key);
    }

}
