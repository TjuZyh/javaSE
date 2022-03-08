package com.javaSE.thread;

public class BankTest {
}

//利用同步机制解决单例模式之懒汉式
class Bank {

    private Bank() {
    }

    private static Bank instance = null;

    public static Bank getInstance() {
        if (instance == null) {
            synchronized (Bank.class) {
                if (instance == null) {
                    instance = new Bank();
                }
            }
        }
        return instance;
    }
}
