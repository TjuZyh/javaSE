package com.javaSE.reflection;

import org.junit.Test;

import java.lang.reflect.Method;

public class MethodTest {

    @Test
    public void test1(){
        Class<Person> personClass = Person.class;
        //getMethods(): 获取当前运行时类及其所有父类中声明为public权限的方法
        Method[] methods = personClass.getMethods();
        for(Method m : methods){
            System.out.println(m);
        }

        System.out.println();

        //getDeclaredMethods(): 获取当前运行时类中声明的所有方法（不包含父类中声明的方法）
        Method[] declaredMethods = personClass.getDeclaredMethods();
        for(Method m : declaredMethods){
            System.out.println(m);
        }
    }
}
