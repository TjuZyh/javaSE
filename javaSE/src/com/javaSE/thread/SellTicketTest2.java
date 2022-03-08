package com.javaSE.thread;

/*
 * 实现三个窗口同时出售100张票
 * */

class TicketThread implements Runnable {

    private int ticket = 100;
    //生成一个锁，注意要保证线程公用同一个锁，所以需要声明为全局
    Object lock = new Object();

    @Override
    public void run() {
        while (true) {

            synchronized (lock){
                if(ticket > 0){
                    System.out.println(Thread.currentThread().getName() + ": " + ticket);
                    ticket--;
                }else {
                    break;
                }
            }

        }
    }
}

public class SellTicketTest2 {
    public static void main(String[] args) {
        TicketThread ticketThread = new TicketThread();
        Thread t1 = new Thread(ticketThread);
        Thread t2 = new Thread(ticketThread);
        Thread t3 = new Thread(ticketThread);

        t1.start();
        t2.start();
        t3.start();

    }
}
