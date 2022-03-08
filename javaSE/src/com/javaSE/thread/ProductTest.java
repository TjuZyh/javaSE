package com.javaSE.thread;


/*
* 经典例题：生产者/消费者问题
*   生产者将产品交给店员，消费者从店员取走产品，店员一次只能持有固定数量的产品（例如20），如果生产者生产更多的产品，店员会通知生产者
*   暂停一下；同样店中没有产品了，店员会通知消费者等一下。
* */


class Clerk{

    private int productCount = 0;

    public synchronized void produceProduct(){
        if(productCount < 20){

            productCount++;
            System.out.println(Thread.currentThread().getName() + "生产了第" + productCount + "个产品");
            notify();
        }else {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void consumeProduct(){
        if(productCount > 0){

            System.out.println(Thread.currentThread().getName() + "消费了第" + productCount + "个产品");
            productCount--;
            notify();
        }else {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Producer extends Thread {
    private Clerk clerk;

    public Producer(Clerk clerk){
        this.clerk = clerk;
    }

    @Override
    public void run() {
        while (true){
            //模拟生产者慢一点生产
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clerk.produceProduct();
        }
    }
}

class Customers extends Thread{
    private Clerk clerk;

    public Customers(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        while (true){
            //模拟消费者慢一点消费
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clerk.consumeProduct();
        }
    }
}

public class ProductTest {
    public static void main(String[] args) {
        Clerk clerk = new Clerk();

        Producer producer1 = new Producer(clerk);
        producer1.setName("producer1");
        producer1.start();

        Customers customers1 = new Customers(clerk);
        customers1.setName("customers1");
        customers1.start();

        Customers customers2 = new Customers(clerk);
        customers2.setName("customers2");
        customers2.start();
    }
}
