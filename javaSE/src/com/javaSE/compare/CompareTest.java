package com.javaSE.compare;


import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;

public class CompareTest {

    @Test
    public void test1(){
        String[] arr = new String[]{"DD","EE","AA","GG"};
        Arrays.sort(arr);
        System.out.println(Arrays.toString(arr));
    }

    @Test
    public void test2(){
        Goods[] goods = new Goods[5];
        goods[0] = new Goods("mac", 12000);
        goods[1] = new Goods("mouse3", 45);
        goods[2] = new Goods("keyboard", 300);
        goods[3] = new Goods("ipad", 6999);
        goods[4] = new Goods("mouse2", 45);

        Arrays.sort(goods);
        System.out.println(Arrays.toString(goods));
    }

    @Test
    public void test3(){
        String[] arr = new String[]{"DD","EE","AA","GG"};

        //实现字符串从大到小排序
        Arrays.sort(arr, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                if(o1 instanceof String && o2 instanceof String){
                    String str1 = (String) o1;
                    String str2 = (String) o2;
                    return -str1.compareTo(str2);
                }
                throw new RuntimeException("exception！");
            }
        });

        System.out.println(Arrays.toString(arr));
    }

    @Test
    public void test4(){
        Goods[] goods = new Goods[5];
        goods[0] = new Goods("mac", 12000);
        goods[1] = new Goods("mouse3", 45);
        goods[2] = new Goods("keyboard", 300);
        goods[3] = new Goods("ipad", 6999);
        goods[4] = new Goods("mouse2", 45);

        Arrays.sort(goods, new Comparator() {

            @Override
            public int compare(Object o1, Object o2) {
                if(o1 instanceof Goods && o2 instanceof Goods){
                    Goods g1 = (Goods) o1;
                    Goods g2 = (Goods) o2;
                    if(g1.getName().equals(g2.getName())){
                        return -Double.compare(g1.getPrice(), g2.getPrice());
                    }else {
                        return g1.getName().compareTo(g2.getName());
                    }
                }
                throw new RuntimeException("exception！");
            }
        });
        System.out.println(Arrays.toString(goods));
    }

    @Test
    public void test5(){
        String javaHome = System.getProperty("java.home");
        System.out.println(javaHome);
    }


}
