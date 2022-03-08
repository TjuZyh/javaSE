package com.javaSE.generic;

import org.junit.Test;

import java.util.Iterator;
import java.util.List;

public class GenericTest {

    @Test
    public void test1(){
        //实例化时指定泛型
        Order<String> order = new Order<>("mac" , 123,"orderT");

        order.setOrderT("aaa");

    }

    @Test
    public void test2(){
        List<Object> list1 = null;
        List<String> list2 = null;

        List<?> list = null;

        list = list1;
        list = list2;

        print(list1);
        print(list2);
    }

    public void print(List<?> list){
        Iterator<?> iterator = list.iterator();
        while(iterator.hasNext()){
            Object object = iterator.next();
            System.out.println(object);
        }
    }
}
