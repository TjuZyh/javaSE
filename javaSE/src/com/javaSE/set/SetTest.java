package com.javaSE.set;

import org.junit.Test;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public class SetTest {

    @Test
    public void test1(){
        Set set = new HashSet();
        set.add(456);
        set.add(123);
        set.add("aaa");
        set.add("bbb");
        set.add(new Person("zyh" , 23));
        set.add(new Person("zyh" , 23));
        set.add(123);

        Iterator iterator = set.iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }

    @Test
    public void test2(){
        Set set = new LinkedHashSet();
        set.add(456);
        set.add(123);
        set.add("aaa");
        set.add("bbb");
        set.add(new Person("zyh" , 23));
        set.add(new Person("zyh" , 23));
        set.add(123);

        Iterator iterator = set.iterator();
        //按照添加的顺序输出
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }
}
