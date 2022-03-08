package com.javaSE.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

interface Human{

    String getBelief();

    void eat(String food);
}

class Superman implements Human{
    @Override
    public String getBelief() {
        return "I can fly";
    }

    @Override
    public void eat(String food) {
        System.out.println("I like " + food);
    }
}

class ProxyFactory{

    //声明为static调用此方法返回一个代理类对象，
    //根据加载到内存中的被代理类，动态的创建一个代理类及其对象
    //obj为被代理类
    public static Object getProxyInstance(Object obj){

        MyInvocationHandler myInvocationHandler = new MyInvocationHandler();

        myInvocationHandler.bind(obj);

        return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(),myInvocationHandler);
    }
}

class MyInvocationHandler implements InvocationHandler{

    //需要使用被代理类的对象进行赋值
    private Object obj;

    public void bind(Object obj){
        this.obj = obj;
    }

    //当通过代理类的对象调用方法a时，会自动调用如下方法invoke()
    //将被代理类要执行的方法a的功能就声明在invoke中
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //method：代理类对象调用的方法，此方法作为了被代理类对象要调用的方法
        //obj：被代理类的对象
        Object returnValue = method.invoke(obj, args);
        //上述方法的返回值就作为当前类中的invoke()
        return returnValue;
    }
}


public class DynamicProxyTest {
    public static void main(String[] args) {
        Superman superman = new Superman();
        //proxyInstance:动态创建的代理类对象
        Human proxyInstance = (Human) ProxyFactory.getProxyInstance(superman);

        proxyInstance.eat("chocolate");
        System.out.println(proxyInstance.getBelief());

        //动态创建代理类，代理 静态代理示例 中的被代理类nikeClothFactory
        NikeClothFactory nikeClothFactory = new NikeClothFactory();
        ClothFactory proxyInstance1 = (ClothFactory) ProxyFactory.getProxyInstance(nikeClothFactory);
        proxyInstance1.produceCloth();
    }
}
