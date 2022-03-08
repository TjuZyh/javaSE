package com.javaSE.set;

import org.junit.Test;

import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

public class TreeSetTest {

    @Test
    public void test1(){
        TreeSet<Integer> treeSet = new TreeSet<>();

        treeSet.add(123);
        treeSet.add(34);
        treeSet.add(56);
        treeSet.add(798);

        Iterator<Integer> iterator = treeSet.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }

    @Test
    public void test2(){
        TreeSet<Person> treeSet = new TreeSet<>();

        //在Person类中重写的比较器，对姓名属性进行排序
        //注意：TreeSet默认用compareTo方法进行比较，所以需要更具体CompareTo方法的实现（二级实现，涉及年龄）
        treeSet.add(new Person("zyh" , 22));
        treeSet.add(new Person("zyh" , 30));
        treeSet.add(new Person("fy" , 20));
        treeSet.add(new Person("pdx" , 5));
        treeSet.add(new Person("dp" , 3));

        for(Person person : treeSet){
            System.out.println(person);
        }
    }

    @Test
    public void test3(){
        Comparator<Person> comparator = new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                if(o1 != null && o2 != null){
                    return Integer.compare(((Person) o1).getAge() , ((Person) o2).getAge());
                }else {
                    throw new RuntimeException("exception!");
                }
            }
        };

        TreeSet<Person> treeSet = new TreeSet<>(comparator);
        treeSet.add(new Person("zyh" , 20));
        treeSet.add(new Person("zyh" , 30));
        treeSet.add(new Person("fy" , 20));
        treeSet.add(new Person("pdx" , 5));
        treeSet.add(new Person("dp" , 3));

        for(Person person : treeSet){
            System.out.println(person);
        }
    }
}
