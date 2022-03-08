package com.javaSE.thread;

public class DeadLockTest {
    public static void main(String[] args) {

        StringBuffer stringBuffer1 = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();

        new Thread(){
            @Override
            public void run() {
                synchronized (stringBuffer1) {
                    stringBuffer1.append("a");
                    stringBuffer2.append(1);

                    //当前已经有一个线程拿着锁1，等睡醒后需要拿锁2
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    synchronized (stringBuffer2) {
                        stringBuffer1.append("b");
                        stringBuffer2.append(2);

                        System.out.println(stringBuffer1);
                        System.out.println(stringBuffer2);
                    }
                }
            }
        }.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (stringBuffer2) {
                    stringBuffer1.append("c");
                    stringBuffer2.append(3);

                    //当前已经有一个线程拿着锁2，等睡醒后需要拿锁1
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    synchronized (stringBuffer1) {
                        stringBuffer1.append("d");
                        stringBuffer2.append(4);

                        System.out.println(stringBuffer1);
                        System.out.println(stringBuffer2);
                    }
                }
            }
        }).start();

    }
}
