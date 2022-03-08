package com.javaSE.thread;

/*
* 两个客户向同一账户中存3000元，每次存1000，存三次，每次存完打印账户余额
* */
class Account {

    private double balance;

    public Account(double balance) {
        this.balance = balance;
    }

    public void deposit(double amt) {
        if (amt > 0) {
            balance += amt;

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + "存入" + amt + "元，余额为： " + balance);
        }
    }
}

class Customer extends Thread {
    private Account acct;

    public Customer(Account acct) {
        this.acct = acct;
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            acct.deposit(1000);
        }
    }
}

public class AccountTest {
    public static void main(String[] args) {
        Account account = new Account(0);
        Customer customer1 = new Customer(account);
        Customer customer2 = new Customer(account);

        customer1.setName("customer1");
        customer2.setName("customer2");

        customer1.start();
        customer2.start();
    }
}
