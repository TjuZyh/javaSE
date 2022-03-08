package com.javaSE.thread;

import java.util.concurrent.locks.ReentrantLock;

class SellThread implements Runnable{

    private int ticket = 100;
    //1. 实例化ReentrantLock
    private ReentrantLock lock = new ReentrantLock();

    @Override
    public void run() {
        while (true){
            try {
                //2. 调用锁定方法lock()
                lock.lock();

                if(ticket > 0){

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + ": " + ticket);
                    ticket--;
                }else {
                    break;
                }

            }finally {
                //3. 调用解锁方法unlock()
                lock.unlock();
            }
        }
    }
}

public class SellTicketTest3 {

    public static void main(String[] args) {
        SellTicket sellTicket = new SellTicket();

        Thread thread1 = new Thread(sellTicket);
        Thread thread2 = new Thread(sellTicket);
        Thread thread3 = new Thread(sellTicket);

        thread1.start();
        thread2.start();
        thread3.start();

    }
}
