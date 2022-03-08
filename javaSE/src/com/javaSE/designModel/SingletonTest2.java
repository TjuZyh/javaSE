package com.javaSE.designModel;

//懒汉式
class Student{

    //1. 私有化类的构造器
    private Student(){

    }
    //2. 声明当前类对象，没有初始化，注意声明为static
    private static Student instance = null;
    //3. 声明方法返回当前类对象
    public static Student getInstance(){
        if(instance == null){
            //注意需要用同步代码块保证线程安全
            synchronized (Student.class){
                if(instance == null){
                    instance = new Student();
                }
            }
        }
        return instance;
    }
}

public class SingletonTest2 {

    public static void main(String[] args) {
        Student student1 = Student.getInstance();
        Student student2 = Student.getInstance();

        System.out.println(student1 == student2);
    }

}
