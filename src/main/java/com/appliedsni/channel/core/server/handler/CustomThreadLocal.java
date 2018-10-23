package com.appliedsni.channel.core.server.handler;

import java.util.HashMap;
import java.util.Map;
 
public class CustomThreadLocal {
 
    private static Map<Thread, Map<String, Object>> threadMap = new HashMap<Thread, Map<String, Object>>();
 
    public static void add(String pKey, Object pObject) {
    	Map<String, Object> valueMap = threadMap.get(Thread.currentThread());
    	if(valueMap == null){
    		valueMap = new HashMap<String, Object>();
    	}
    	valueMap.put(pKey, pObject);
        threadMap.put(Thread.currentThread(), valueMap);
    }
 
    public static void remove(Object object) {
        threadMap.remove(Thread.currentThread());
    }
 
    public static Object get(String pKey) {
        return threadMap.get(Thread.currentThread()).get(pKey);
    }

}
