package com.javaSE.proxy;


interface ClothFactory{

    void produceCloth();

}

//代理类
class ProxyClothFactory implements ClothFactory{

    //用于被代理类对象进行实例化
    private ClothFactory factory;

    public ProxyClothFactory(ClothFactory factory){
        this.factory = factory;
    }

    @Override
    public void produceCloth() {
        System.out.println("代理工厂进行一些准备工作");
        System.out.println("...");

        factory.produceCloth();

        System.out.println("...");
        System.out.println("代理工厂做一些后续的收尾工作");
    }
}

//被代理类
class NikeClothFactory implements ClothFactory{
    @Override
    public void produceCloth() {
        System.out.println("NIKE工厂开始生产一批衣服");
    }
}

public class StaticProxyTest {

    public static void main(String[] args) {
        //创建被代理类的对象
        NikeClothFactory nikeClothFactory = new NikeClothFactory();
        //创建代理类的对象
        ProxyClothFactory proxyClothFactory = new ProxyClothFactory(nikeClothFactory);

        proxyClothFactory.produceCloth();

    }



}
