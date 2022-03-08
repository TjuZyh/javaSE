package com.javaSE.set;

import org.junit.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class MapTest {

    @Test
    public void test1(){
        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put("zyh" , 21);
        hashMap.put("fy" , 20);
        hashMap.put("pdx" , 12);
        hashMap.put("dp" , 5);

        System.out.println(hashMap);

        Integer remove = hashMap.remove("dp");
        System.out.println(remove);
        System.out.println(hashMap);
    }

    @Test
    public void test2(){
        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put("zyh" , 21);
        hashMap.put("fy" , 20);
        hashMap.put("pdx" , 12);
        hashMap.put("dp" , 5);

        System.out.println(hashMap.get("zyh"));
        System.out.println(hashMap.containsKey("fy"));
        System.out.println(hashMap.containsValue(21));
        System.out.println(hashMap.size());
    }

    @Test
    public void test3(){
        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put("zyh" , 21);
        hashMap.put("fy" , 20);
        hashMap.put("pdx" , 12);
        hashMap.put("dp" , 5);

        Iterator<String> iterator = hashMap.keySet().iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }

        Collection<Integer> values = hashMap.values();
        for(Integer value : values){
            System.out.println(value);
        }

        Iterator<String> iterator1 = hashMap.keySet().iterator();
        while (iterator1.hasNext()){
            String key = iterator1.next();
            Integer value = hashMap.get(key);
            System.out.println(key + ": " + value);
        }


    }


}
