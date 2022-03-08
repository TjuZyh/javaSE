package com.javaSE.newCharacter.stream;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

public class StreamAPITest1 {

    //中间操作：筛选与切片
    @Test
    public void test1(){
        List<Employee> empData = EmployeeData.getEmpData();
        //filter(Predicate p): 接收Lambda，从流中排除某些元素
        empData.stream().filter(e -> e.getAge() > 10).forEach(System.out::println);
        System.out.println();

        //limit(n): 截断流，使其元素不超过给定数量
        empData.stream().limit(3).forEach(System.out::println);
        System.out.println();

        //skip(n): 跳过元素，返回一个跳过了前n个元素的流；若不足n个则返回空集合
        empData.stream().skip(2).forEach(System.out::println);
        System.out.println();

        //distinct(n): 筛选，通过流所生成的hashcode()以及equals()去除重复元素
        empData.add(new Employee(1006, "aaa", 19, 0));
        empData.add(new Employee(1006, "aaa", 19, 0));
        empData.add(new Employee(1006, "aaa", 19, 0));

        empData.stream().distinct().forEach(System.out::println);
    }

    //映射
    @Test
    public void test2(){
        //map(function f): 接收一个函数作为参数，将元素转换成其他形式或提取信息，该函数会被应用到每个元素上，并将其映射成一个新的元素
        List<String> list = Arrays.asList("aa", "bb", "cc", "dd");
        list.stream().map(s -> s.toUpperCase(Locale.ROOT)).forEach(System.out::println);

        //获取员工姓名长度大于2的员工姓名
        Stream<String> stringStream = EmployeeData.getEmpData().stream().map(Employee::getName);
        stringStream.filter(name -> name.length() > 2).forEach(System.out::println);

        //flagMap(function f): 接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有的流连接成一个流
        //将字符串进行拆分
        Stream<Character> characterStream = list.stream().flatMap(StreamAPITest1::forStringToStream);
        characterStream.forEach(System.out::println);

    }

    public static Stream<Character> forStringToStream(String str){
        ArrayList<Character> characters = new ArrayList<>();
        for(Character character : str.toCharArray()){
            characters.add(character);
        }
        return characters.stream();
    }

    //排序
    @Test
    public void test3(){
        //sorted():自然排序
        List<Integer> list = Arrays.asList(13, 34, 6, 4, 8, 0, -1, 5);
        list.stream().sorted().forEach(System.out::println);

        //sorted(Comparator com): 定值排序
        //根据年龄升序排序；若年龄相同则按序号升序排列
        List<Employee> employeeList = EmployeeData.getEmpData();
        employeeList.stream().sorted( (e1 , e2) ->{
            int flag = Integer.compare(e1.getAge() , e2.getAge());
            if(flag != 0){
                return flag;
            }else{
                return Long.compare(e1.getId() , e2.getId());
            }
        }
        ).forEach(System.out::println);
    }


}
