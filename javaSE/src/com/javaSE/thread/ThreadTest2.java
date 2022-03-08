package com.javaSE.thread;

//1.创建一个实现了Runnable接口的类
class MThread implements Runnable{

    //2.实现类实现抽象方法：run()
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            if(i % 2 == 0){
                System.out.println(Thread.currentThread().getName() + ": " + i);
            }
        }
    }
}

public class ThreadTest2 {

    public static void main(String[] args) {
        //3. 创建实现类的对象
        MThread mThread = new MThread();
        //4. 将此对象作为参数传递到Thread类的构造器中，创建Thread类的对象
        Thread thread1 = new Thread(mThread);
        //5.通过Thread类的对象调用start()
        //start:1. 启动线程 2. 调用当前线程的run(), 继续调用Runnable类型的target的run()
        thread1.start();

        Thread thread2 = new Thread(mThread);
        thread2.start();

    }
}
