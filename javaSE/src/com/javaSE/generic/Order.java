package com.javaSE.generic;

import java.util.ArrayList;
import java.util.List;

public class Order<T> {

    String orderName;
    long orderId;
    T orderT;

    public Order() {
    }

    public Order(String orderName, long orderId, T orderT) {
        this.orderName = orderName;
        this.orderId = orderId;
        this.orderT = orderT;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public T getOrderT() {
        return orderT;
    }

    public void setOrderT(T orderT) {
        this.orderT = orderT;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderName='" + orderName + '\'' +
                ", orderId=" + orderId +
                ", orderT=" + orderT +
                '}';
    }

    //自定义泛型方法
    public <E> List<E> copyFromArrayToList(E[] array){

        ArrayList<E> arrayList = new ArrayList<>();

        for(E e : array){
            arrayList.add(e);
        }
        return arrayList;

    }




}
