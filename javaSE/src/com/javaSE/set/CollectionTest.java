package com.javaSE.set;

import org.junit.Test;

import javax.xml.bind.SchemaOutputResolver;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class CollectionTest {

    @Test
    public void test1(){
        Collection collection = new ArrayList();
        collection.add("aaa");
        collection.add("bb");
        collection.add(new String("hello"));
        collection.add(123);
        collection.add(new Person("zyh" , 23));

        System.out.println(collection.size());

        Collection collection1 = new ArrayList();
        collection1.add(456);
        collection1.add("ccc");
        collection.addAll(collection1);

        System.out.println(collection);
        System.out.println(collection.isEmpty());

        //collection.clear();

        System.out.println(collection.isEmpty());

        System.out.println(collection.contains("aaa"));
        System.out.println(collection.contains(new String("hello")));
        //contain利用当前对象的equals()方法进行比对
        //如果当前对象未重写equals方法，默认调用==方法，下面的写法相当于创建了新的对象，地址不同，故不包含
        System.out.println(collection.contains(new Person("zyh" , 23)));
    }

    @Test
    public void test2(){
        //remove(obj): 从当前集合中移除obj元素，注意会先判断equal后删除
        Collection collection = new ArrayList();
        collection.add("aaa");
        collection.add("bb");
        collection.add(new String("hello"));
        collection.add(123);
        collection.add(new Person("zyh" , 23));

        collection.remove(123);
        System.out.println(collection);

        collection.remove(new Person("zyh" , 23));
        System.out.println(collection);
    }

    @Test
    public void test3(){
        //retainAll(Collection coll): 相当于交集，获取当前集合和coll集合相交，返回给当前集合
        Collection collection = new ArrayList();
        collection.add("aaa");
        collection.add("bb");
        collection.add(new String("hello"));
        collection.add(123);
        collection.add(new Person("zyh" , 23));

        Collection collection1 = new ArrayList();
        collection1.add(123);
        collection1.add(456);
        collection1.add(789);

        collection.retainAll(collection1);
        System.out.println(collection);

        //hashcode()
        System.out.println(collection.hashCode());

        //toArray(): collection转为Array
        Object[] array = collection.toArray();
        for(int i = 0 ; i < array.length ; i++){
            System.out.println(array[i]);
        }

        //Array.asList(): Array转为collection
        List<String> list = Arrays.asList(new String[]{"aaa", "bbb"});
        System.out.println(list);

    }
}
