package com.javaSE.reflection;

import org.junit.Test;

import java.util.Random;

public class NewInstanceTest {

    @Test
    public void test1() throws IllegalAccessException, InstantiationException {
        Class<Person> personClass = Person.class;
        //newInstance方法：调用此方法，创建对应的运行时类的对象，内部调用了运行时类的空参构造器
        //注意需要运行时类提供公有的空参构造器
        Person person = personClass.newInstance();
        System.out.println(person);
    }

    @Test
    public void test2(){
        int num = new Random().nextInt(2);
        String classPath = "";
        switch (num){
            case 0:
                classPath = "java.util.Date";
                break;
            case 1:
                classPath = "com.javaSE.reflection.Person";
                break;
        }
        try {
            Object instance = getInstance(classPath);
            System.out.println(instance);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

    }

    public Object getInstance(String classPath) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class<?> aClass = Class.forName(classPath);
        Object newInstance = aClass.newInstance();
        return newInstance;

    }
}
