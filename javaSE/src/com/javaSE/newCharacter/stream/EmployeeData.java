package com.javaSE.newCharacter.stream;

import java.util.ArrayList;
import java.util.List;

public class EmployeeData {
    public static List<Employee> getEmpData(){
        ArrayList<Employee> employees = new ArrayList<>();
        Employee employee1 = new Employee(1001, "zyh", 23, 1);
        Employee employee2 = new Employee(1002, "fy", 21, 0);
        Employee employee3 = new Employee(1003, "pdx", 8, 1);
        Employee employee4 = new Employee(1004, "dp", 3, 0);
        Employee employee5 = new Employee(1005, "herry", 30, 1);

        employees.add(employee1);
        employees.add(employee2);
        employees.add(employee3);
        employees.add(employee4);
        employees.add(employee5);

        return employees;
    }
}
