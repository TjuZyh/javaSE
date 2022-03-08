package com.javaSE.newCharacter.lambda;

import com.javaSE.reflection.Person;
import org.junit.Test;

import java.util.Comparator;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class MethodTest {
    @Test
    public void test1(){
        Consumer<String> consumer = str -> System.out.println(str);
        consumer.accept("hello world");

        //方法引用
        //对象 :: 实例方法
        Consumer<String> consumer1 = System.out :: println;
        consumer1.accept("world hello");
    }

    @Test
    public void test2(){
        Person person = new Person("zyh", 23);
        Supplier<String> supplier = () -> person.getName();
        System.out.println(supplier.get());

        //方法引用
        //对象 :: 实例方法
        Supplier<String> supplier1 = person :: getName;
        System.out.println(supplier1.get());
    }

    @Test
    public void test3(){
        Comparator<Integer> comparator = (o1 , o2) -> Integer.compare(o1 , o2);
        System.out.println(comparator.compare(12, 21));

        //方法引用
        //类 :: 实例方法
        Comparator<Integer> comparator1 = Integer :: compare;
        System.out.println(comparator1.compare(34,43));
    }

    @Test
    public void test4(){
        Function<Double , Long> function = d -> Math.round(d);
        System.out.println(function.apply(13.4));

        //方法引用
        //类 :: 实例方法
        Function<Double , Long> function1 = Math :: round;
        System.out.println(function1.apply(3.14));
    }

    @Test
    public void test5(){
        Comparator<String> comparator = (s1 , s2) -> s1.compareTo(s2);
        System.out.println(comparator.compare("cba" , "abc"));

        //方法引用
        //类 ::实例方法
        Comparator<String> comparator1 = String :: compareTo;
        System.out.println(comparator1.compare("abc" , "abc"));
    }

    @Test
    public void test6(){
        BiPredicate<String , String> biPredicate = (s1 , s2) -> s1.equals(s2);
        System.out.println(biPredicate.test("abc" , "abc"));
        //方法引用
        //类 ::实例方法
        BiPredicate<String , String> biPredicate1 = String :: equals;
        System.out.println(biPredicate1.test("aaa" , "bbb"));
    }




}
