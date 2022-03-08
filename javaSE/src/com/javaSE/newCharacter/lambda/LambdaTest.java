package com.javaSE.newCharacter.lambda;

import org.junit.Test;

import java.util.Comparator;

public class LambdaTest {
    @Test
    public void test1(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("hello world!");
            }
        };
        runnable.run();

        Runnable runnable1 = () -> System.out.println("hello new World!");
        runnable1.run();
    }

    @Test
    public void test2(){
        Comparator<Integer> comparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o1 , o2);
            }
        };
        System.out.println(comparator.compare(21, 12));

        Comparator<Integer> comparator1 = ((o1, o2) -> Integer.compare(o1 , o2));
        System.out.println(comparator1.compare(13,31));

        Comparator<Integer> comparator2 = (Integer::compareTo);
        System.out.println(comparator2.compare(33,33));

    }


}
