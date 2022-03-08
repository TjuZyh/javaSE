package com.javaSE.newCharacter.stream;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;


public class StreamAPITest2 {
    //匹配与查找
    List<Employee> empData = EmployeeData.getEmpData();
    @Test
    public void test1(){
        //allMatch(): 检查是否匹配所有元素
        //检查是否全部年龄都大于2
        boolean allMatch = empData.stream().allMatch(e -> e.getAge() > 2);
        System.out.println(allMatch);

        //anyMatch(): 检查是否至少匹配一个元素
        //检查是否有一个员工的姓名长于4
        boolean anyMatch = empData.stream().anyMatch(e -> e.getName().length() == 4);
        System.out.println(anyMatch);

        //noneMatch(): 检查是否没有匹配的元素
        //检查姓名是否存在z
        boolean noneMatch = empData.stream().noneMatch(e -> e.getName().equals("z"));
        System.out.println(noneMatch);

        //findFirst():返回第一个元素
        //findAny():返回任意元素
        Optional<Employee> employee = empData.stream().findFirst();
        System.out.println(employee);

        //count(): 返回流中元素的个数
        long count = empData.stream().filter(e -> e.getAge() < 10).count();
        System.out.println(count);

        //max(): 返回流中最大的值
        //min(): 返回流中最小的值
        Optional<Employee> max = empData.stream().max((e1, e2) -> Integer.compare(e1.getAge(), e2.getAge()));
        System.out.println(max);

        //forEach(): 内部迭代
        empData.stream().forEach(System.out::println);
    }

    //归约
    @Test
    public void test2(){
        //reduce(T identity , BinaryOperator):可以将流中元素反复结合起来，得到一个值
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Integer sum = list.stream().reduce(0, Integer::sum);
        System.out.println(sum);

        //计算员工的年龄总和
        Optional<Integer> sumAge = empData.stream().map(e -> e.getAge()).reduce((e1, e2) -> e1 + e2);
        Optional<Integer> sumAge1 = empData.stream().map(Employee :: getAge).reduce(Integer :: sum);

        System.out.println(sumAge);
        System.out.println(sumAge1);
    }

    //收集
    @Test
    public void test3(){
        //collect(Collectors c): 将流转换为其他形式（List或Set）
        //查找性别为男的员工
        List<Employee> employeeList = empData.stream().filter(e -> e.getGender() == 1).collect(Collectors.toList());
        employeeList.forEach(System.out::println);

        System.out.println();
        Set<Employee> employeeSet = empData.stream().filter(e -> e.getAge() > 10).collect(Collectors.toSet());
        employeeSet.forEach(System.out::println);

    }
}
