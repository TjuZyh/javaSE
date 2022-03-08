package com.javaSE.ood;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WalletTest {
    @Test
    public void test1(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        String format = simpleDateFormat.format(date);
        System.out.println(format);
    }
}

class Wallet{
    private String id;
    private String createTime;
    private double balance;
    private String balanceLastModifiedTime;


    public String getCreateTime() {
        return createTime;
    }

    public double getBalance() {
        return balance;
    }

    public String getId() {
        return id;
    }

    public String getBalanceLastModifiedTime() {
        return balanceLastModifiedTime;
    }

    public void increaseBalance(double balance){
        if(balance < 0){
            throw new RuntimeException("...");
        }
        this.balance += balance;
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        this.balanceLastModifiedTime = simpleDateFormat.format(date);
    }
}
