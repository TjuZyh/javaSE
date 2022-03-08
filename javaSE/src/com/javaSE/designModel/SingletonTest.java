package com.javaSE.designModel;

//饿汉式
class People{

    //1. 私有化类的构造器
    private People() {

    }
    //2. 内部创建类的对象
    //注意要声明为静态的
    private static People instance = new People();

    //3. 提供公共的静态方法，返回类的对象
    public static People getInstance(){
        return instance;
    }
}

public class SingletonTest {
    public static void main(String[] args) {

        People people1 = People.getInstance();
        People people2 = People.getInstance();

        //true
        System.out.println(people1 == people2);
    }
}
