package com.wfy.web.service;

import com.wfy.web.common.ServerResponse;
import com.wfy.web.model.Employee;
import com.wfy.web.utils.RefCount;

import java.util.List;

/**
 * Created by Administrator on 2017/8/26.
 */
public interface IEmployeeService {

    List<Employee> getEmployees(RefCount refCount, String key, String dept, int pageIndex, int pageSize);

    Employee getEmployeeByName(String name);

    void updateEmployee(Employee employee);

    Employee getEmployeeById(String id);

    boolean delete(String id);

    List<Employee> getDeletedEmployees();

    Employee addEmployee(String name);

    boolean recover(String id);

    ServerResponse<String> relateUser(String username, String password, String empId);

    void unrelateUser(String id);

    long countEmployee();
}
