package com.javaSE.set;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class IteratorTest {

    @Test
    public void test1(){
        Collection collection = new ArrayList();
        collection.add("aaa");
        collection.add("bb");
        collection.add(new String("hello"));
        collection.add(123);
        collection.add(new Person("zyh" , 23));

        //iterator():返回Iterator接口的实例，用于遍历集合元素
        Iterator iterator = collection.iterator();
        //hasNext()：判断是否还有下一个元素
        //next()：1. 指针下移 2. 将下移后集合位置上的元素返回
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }

        Iterator iter = collection.iterator();
        while (iter.hasNext()){
            Object current = iter.next();
            if("aaa".equals(current)){
                iter.remove();
            }
        }
        System.out.println(collection);

    }
}
