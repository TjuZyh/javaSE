package com.javaSE.reflection;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class CallTest {

    @Test
    public void testField() throws Exception {
        Class<Person> personClass = Person.class;

        //创建运行时类的对象
        Person person = personClass.newInstance();

        //1. getDeclaredField()：获取运行时类中指定变量名的属性
        Field name = personClass.getDeclaredField("name");

        //2. 保证当前属性是可访问的
        name.setAccessible(true);

        //3. 获取设置指定对象的属性值
        //属性.set(运行时类对象，属性值)
        name.set(person , "zyh");

        //属性.get(运行时类对象)
        System.out.println(name.get(person));
    }

    @Test
    public void testMethod() throws Exception {

        Class personClass = Class.forName("com.javaSE.reflection.Person");
        Person person = (Person) personClass.newInstance();

        //getDeclaredMethod(): 参数一：方法名，参数二：形参类型
        Method love = personClass.getDeclaredMethod("love", String.class);
        love.setAccessible(true);

        //invoke(): 参数一：运行时类对象 参数二：形参值
        //invoke的返回值即为原函数的返回值
        Object returnValue = love.invoke(person, "fy");
        System.out.println(returnValue);
    }



}
