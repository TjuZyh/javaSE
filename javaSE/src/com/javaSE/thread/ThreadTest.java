package com.javaSE.thread;

//1、创建一个继承Thread的子类
class MyThread extends Thread{
    //2、重写Thread类中的run()
    @Override
    public void run() {
        for(int i = 0 ; i < 100 ; i++){
            if(i % 2 == 0){
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + ": " + i);
            }
            if(i % 20 == 0){
                //释放当前cpu的执行权
                yield();
            }
        }
    }
}

public class ThreadTest {
    public static void main(String[] args) {
        //3、创建Thread类子类的对象
        MyThread thread1 = new MyThread();
        //4、通过该对象调用start()
        //start() 1.启动当前线程 2.调用当前线程的run()
        thread1.start();

        MyThread thread2 = new MyThread();
        thread2.start();

        //创建Thread类的匿名子类方式
        new Thread(){
            @Override
            public void run() {
                for(int i = 0 ; i < 100 ; i++){
                    if(i % 2 != 0){
                        System.out.println(Thread.currentThread().getName() + ": " + i);
                    }
                }
            }
        }.start();

        //模拟主线程
        for(int i = 0 ; i < 10 ; i++){
            if(i == 5){
                try {
                    //在线程a中调用线程b的join()，此时线程a进入阻塞状态，知道线程b执行完之后，线程a结束阻塞状态
                    thread1.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + ": " + "main Thread");
        }
    }
}
