package com.javaSE.newCharacter.functionalInterface;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class FuncInterface {
    @Test
    public void test1(){
        List<String> list = Arrays.asList("aaa" , "bba" , "acd" , "vvv");
        /*List<String> filterList = filterString(list, new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return s.contains("a");
            }
        });*/

        List<String> filterList = filterString(list , s -> s.contains("a"));
        System.out.println(filterList);
    }

    //过滤Predicate中定义过滤逻辑的数组
    public List<String> filterString(List<String> list , Predicate<String> predicate){
        ArrayList<String> arrayList = new ArrayList<>();
        for(String str : list){
            if(predicate.test(str)){
                arrayList.add(str);
            }
        }
        return arrayList;
    }
}
