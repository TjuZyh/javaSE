package com.javaSE.reflection;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionTest {

    //反射之前，对于Person类的操作
    @Test
    public void test1(){
        //1. 创建Person类的对象
        Person person = new Person("zyh", 23);

        //2. 通过对象，可以调用其内部的公有的属性以及方法
        person.show();
        System.out.println(person.toString());
    }


    //使用反射，对于Person的操作
    @Test
    public void test2() throws Exception {
        Class<Person> personClass = Person.class;
        //1. 通过反射，创建Person类对象
        Constructor<Person> constructor = personClass.getConstructor(String.class, int.class);
        Person person = constructor.newInstance("fy", 20);
        System.out.println(person);
        //2. 通过反射，调用对象的公有属性、方法
        Method show = personClass.getDeclaredMethod("show");
        show.invoke(person);

        System.out.println();

        //3. 通过反射，可以调用Person类的私有结构，包括私有的构造器，属性以及方法
        Method love = personClass.getDeclaredMethod("love");
        love.setAccessible(true);
        love.invoke(person);

        Field age = personClass.getDeclaredField("age");
        age.setAccessible(true);
        age.set(person , 18);
        System.out.println(person);
    }

    @Test
    public void test3() throws ClassNotFoundException {
        //1. 调用运行时类的属性：.class
        Class<Person> personClass1 = Person.class;
        System.out.println(personClass1);

        //2. 通过运行时类的对象，调用getClass方法
        Person person = new Person();
        Class personClass2 = person.getClass();
        System.out.println(personClass2);

        //3. 调用Class的静态方法：forName（String classPath）
        Class personClass3 = Class.forName("com.javaSE.reflection.Person");
        System.out.println(personClass3);

        //4. 使用类加载器：classLoader
        ClassLoader classLoader = ReflectionTest.class.getClassLoader();
        Class personClass4 = classLoader.loadClass("com.javaSE.reflection.Person");
        System.out.println(personClass4);

    }

}
