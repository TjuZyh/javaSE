package com.javaSE.thread;

/*
* 实现两个线程交替打印数字，1 - 100
* */

class Number implements Runnable{

    private int number = 1;

    @Override
    public void run() {
        while (true) {

            synchronized (this) {
                //唤醒wait
                notify();

                if(number <= 100){

                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println(Thread.currentThread().getName() + ": " + number);
                    number++;

                    //打印后该进入阻塞状态
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else {
                    break;
                }
            }
        }
    }
}

public class CommunicationTest {
    public static void main(String[] args) {
        Number number = new Number();

        Thread t1 = new Thread(number);
        Thread t2 = new Thread(number);

        t1.start();
        t2.start();


    }
}
