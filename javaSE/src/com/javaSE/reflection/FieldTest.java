package com.javaSE.reflection;

import org.junit.Test;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class FieldTest {
    @Test
    public void test1(){
        Class<Person> personClass = Person.class;
        //getFields(): 获取当前运行时类及其父类中声明为public访问权限的属性
        Field[] fields = personClass.getFields();
        for(Field f : fields){
            System.out.println(f);
        }

        System.out.println();

        //getDeclaredFields(): 获取当前运行时类中声明的所有属性（不包括父类中声明的属性）
        Field[] declaredFields = personClass.getDeclaredFields();
        for(Field f : declaredFields){
            System.out.println(f);
        }
    }

    @Test
    public void test2(){
        Class<Person> personClass = Person.class;
        Field[] declaredFields = personClass.getDeclaredFields();
        for(Field f : declaredFields){
            //1. 权限修饰符
            int modifiers = f.getModifiers();
            System.out.println(Modifier.toString(modifiers));

            //2. 数据类型
            Class<?> type = f.getType();
            System.out.println(type.getName());

            //3. 变量名
            String name = f.getName();
            System.out.println(name);

            System.out.println();

        }
    }
}
