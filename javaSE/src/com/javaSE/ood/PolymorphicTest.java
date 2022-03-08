package com.javaSE.ood;

public class PolymorphicTest {
    public static void main(String[] args) {
        //父类的引用指向子类的对象
        Person person = new Student();
        //student working
        person.work();
    }
}

class Person{
    private String name;
    private int age;

    public void eat(){
        System.out.println("person eat!");
    }

    public void work(){
        System.out.println("person working");
    }
}

class Student extends Person{
    private String school;

    @Override
    public void work() {
        System.out.println("student working");
    }
}