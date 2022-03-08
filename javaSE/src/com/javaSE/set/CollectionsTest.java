package com.javaSE.set;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CollectionsTest {

    @Test
    public void test1(){
        ArrayList<Integer> list = new ArrayList<>();
        list.add(123);
        list.add(456);
        list.add(789);
        list.add(3412);
        list.add(1233);

        System.out.println(list);

        Collections.reverse(list);
        System.out.println(list);

        Collections.shuffle(list);
        System.out.println(list);

        Collections.sort(list);
        System.out.println(list);

        Collections.swap(list,2,3);
        System.out.println(list);

        int frequency = Collections.frequency(list, 123);
        System.out.println(frequency);

        List<Integer> list1 = Collections.synchronizedList(list);



    }
}
