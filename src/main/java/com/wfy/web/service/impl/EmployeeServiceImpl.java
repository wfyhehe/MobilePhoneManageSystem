package com.wfy.web.service.impl;

import com.mysql.cj.core.util.StringUtils;
import com.wfy.web.dao.DeptDao;
import com.wfy.web.dao.EmployeeDao;
import com.wfy.web.model.Employee;
import com.wfy.web.model.EmployeeType;
import com.wfy.web.service.IEmployeeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/26.
 */
@Service("iEmployeeService")
@Transactional
public class EmployeeServiceImpl implements IEmployeeService {

    @Resource
    private EmployeeDao employeeDao;

    @Resource
    private DeptDao deptDao;

    @Override
    public List<Employee> getEmployees(String name, String dept) {

        if (!StringUtils.isEmptyOrWhitespaceOnly(dept)) {
            List<String> deptIds = new ArrayList<>();
            return employeeDao.search(name.trim(), dept.trim());
        } else {
            if (!StringUtils.isEmptyOrWhitespaceOnly(name)) {
                return employeeDao.search(name.trim());
            } else {
                return employeeDao.getAll();
            }
        }
    }

    @Override
    public List<Employee> getDeletedEmployees() {
        return employeeDao.getDeleted();
    }

    @Override
    public Employee addEmployee(String name) {
        Employee employee = new Employee(name, EmployeeType.OTHER);
        String id = employeeDao.save(employee);
        employee.setId(id);
        return employee;
    }

    @Override
    public boolean recover(String id) {
        return employeeDao.recover(id);
    }

    @Override
    public void updateEmployee(Employee employee) {
        employeeDao.update(employee);
    }

    @Override
    public Employee getEmployeeById(String id) {
        return employeeDao.getEmployeeById(id);
    }

    @Override
    public Employee getEmployeeByName(String name) {
        return employeeDao.getEmployeeByName(name);
    }

    @Override
    public boolean delete(String id) {
        Employee employee = employeeDao.getEmployeeById(id);
        if (employee == null) {
            return false;
        }
        employee.setDeleted(true);
        employeeDao.update(employee);
        return true;
    }


}
