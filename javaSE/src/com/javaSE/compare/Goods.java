package com.javaSE.compare;

public class Goods implements Comparable {

    private String name;
    private double price;

    public Goods() {
    }

    public Goods(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }

    //重写compareTo方法，实现按商品价格从大到小排序，价格相同按名称排序
    @Override
    public int compareTo(Object o) {
        if(o instanceof Goods){
            Goods goods = (Goods) o;
            //方式一：手写比较方法
            if(this.price > goods.price){
                return 1;
            }else if(this.price < goods.price){
                return -1;
            }else {
                return this.name.compareTo(goods.name);
            }
            //方式二：利用Double的比较方法
            //return Double.compare(goods.price , this.price);
        }
        throw new RuntimeException("传入的数据类型不一致！");
    }
}
