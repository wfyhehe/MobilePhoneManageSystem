package com.wfy.web.service;

import com.wfy.web.model.Employee;

import java.util.List;

/**
 * Created by Administrator on 2017/8/26.
 */
public interface IEmployeeService {

    List<Employee> getEmployees(String key,String dept);

    Employee getEmployeeByName(String name);

    void updateEmployee(Employee employee);

    Employee getEmployeeById(String id);

    boolean delete(String id);

    List<Employee> getDeletedEmployees();

    Employee addEmployee(String name);

    boolean recover(String id);
}
