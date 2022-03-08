package com.javaSE.thread;

/*
 * 实现三个窗口同时出售100张票（存在线程安全问题）
 * */

class SellTicket extends Thread {
    private static int ticket = 100;

    @Override
    public void run() {
        while (true) {
            show();
            if(ticket <= 0){
                break;
            }
        }
    }

    private static synchronized void show() {
        if (ticket > 0) {
            System.out.println(Thread.currentThread().getName() + ": sell " + ticket + "th ticket");
            ticket--;
        }
    }
}

public class SellTicketTest {
    public static void main(String[] args) {
        SellTicket sellTicket1 = new SellTicket();
        SellTicket sellTicket2 = new SellTicket();
        SellTicket sellTicket3 = new SellTicket();

        sellTicket1.setName("window1");
        sellTicket2.setName("window2");
        sellTicket3.setName("window3");

        sellTicket1.start();
        sellTicket2.start();
        sellTicket3.start();
    }
}
