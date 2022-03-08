package com.javaSE.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

class TestThread1 implements Runnable{
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            if(i % 2 == 0){
                System.out.println(Thread.currentThread().getName() + ": " + i);
            }
        }
    }
}

class TestThread2 implements Runnable{
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            if(i % 2 != 0){
                System.out.println(Thread.currentThread().getName() + ": " + i);
            }
        }
    }
}

public class ThreadPool {
    public static void main(String[] args) {
        //1. 提供指定线程数量的线程池
        ExecutorService service = Executors.newFixedThreadPool(10);

        //也可以设置线程池的属性
        /*ThreadPoolExecutor service1 = (ThreadPoolExecutor) service;
        service1.setCorePoolSize(15);*/


        //2. 执行指定的线程的操作，需要提供实现Runnable接口或Callable接口实现类的对象
        //execute方法适用Runnable
        service.execute(new TestThread1());
        service.execute(new TestThread2());
        //submit方法适用于Callable
        //service.submit(Callable callable);

        //3. 关闭连接池
        service.shutdown();
    }
}
