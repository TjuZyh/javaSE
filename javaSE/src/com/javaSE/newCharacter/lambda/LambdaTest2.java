package com.javaSE.newCharacter.lambda;

import org.junit.Test;

import java.util.Comparator;
import java.util.function.Consumer;

public class LambdaTest2 {

    //语法格式一：无参，无返回值
    @Test
    public void test1(){

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("hello world!");
            }
        };
        runnable.run();

        System.out.println();

        Runnable runnable1 = () -> {
            System.out.println("hello new World!");
        };

        runnable1.run();
    }

    //语法格式二：需要一个参数，无返回值
    @Test
    public void test2(){
        Consumer<String> consumer1 = new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        };
        consumer1.accept("consumer1");

        System.out.println();

        Consumer<String> consumer2 = (String str) ->{
            System.out.println(str);
        };
        consumer2.accept("consumer2");
    }

    //语法格式三：数据类型可以省略，因为可以由编译器推断得出，称为类型推断
    @Test
    public void test3(){
        Consumer<String> consumer1 = (String str) ->{
            System.out.println(str);
        };
        consumer1.accept("consumer2");

        System.out.println();

        Consumer<String> consumer2 = (str) ->{
            System.out.println(str);
        };
        consumer2.accept("consumer3");
    }

    //语法格式四：若只需要一个参数时，参数的小括号可以省略
    @Test
    public void test4(){
        Consumer<String> consumer1 = (str) ->{
            System.out.println(str);
        };
        consumer1.accept("consumer3");

        System.out.println();

        Consumer<String> consumer2 = str ->{
            System.out.println(str);
        };
        consumer2.accept("consumer4");
    }

    //语法格式五：需要两个或以上的参数，多条执行语句，并可以有返回值
    @Test
    public void test5(){
        Comparator<Integer> comparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                System.out.println(o1);
                System.out.println(o2);
                return o1.compareTo(o2);
            }
        };
        comparator.compare(12,21);

        System.out.println();

        Comparator<Integer> comparator1 = (o1, o2) -> {
            System.out.println(o1);
            System.out.println(o2);
            return o1.compareTo(o2);
        };

        comparator1.compare(34,43);
    }

    //语法格式六：当lambda体只有一条语句时，return与大括号若有可以省略
    @Test
    public void test6(){
        Comparator<Integer> comparator = (o1 , o2) -> o1.compareTo(o2);
        System.out.println(comparator.compare(12,21));
    }

}
