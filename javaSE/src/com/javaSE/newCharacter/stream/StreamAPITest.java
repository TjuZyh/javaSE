package com.javaSE.newCharacter.stream;

import org.junit.Test;

import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamAPITest {

    @Test
    public void test1(){
        List<Employee> employeeList = EmployeeData.getEmpData();

        //返回一个顺序流
        Stream<Employee> stream = employeeList.stream();

        //返回一个并行流
        Stream<Employee> employeeStream = employeeList.parallelStream();
    }

    @Test
    public void test2(){
        int[] arr = {1, 2, 3, 4, 5, 6};
        //调用Arrays类的stream方法，返回一个流
        IntStream stream = Arrays.stream(arr);

    }

    @Test
    public void test3(){
        //通过Stream的of()
        Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5, 6);

    }

    @Test
    public void test4(){
        //创建无限流
        //迭代
        Stream.iterate(0 , t -> t + 2).limit(10).forEach(System.out :: println);

        //生成
        Stream.generate(Math :: random).limit(10).forEach(System.out :: println);
    }
}
