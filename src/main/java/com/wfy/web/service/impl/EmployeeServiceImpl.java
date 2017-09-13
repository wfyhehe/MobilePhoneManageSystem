package com.wfy.web.service.impl;

import com.wfy.web.common.ServerResponse;
import com.wfy.web.dao.DeptDao;
import com.wfy.web.dao.EmployeeDao;
import com.wfy.web.dao.UserDao;
import com.wfy.web.model.Employee;
import com.wfy.web.model.User;
import com.wfy.web.service.IEmployeeService;
import com.wfy.web.utils.MD5Util;
import com.wfy.web.utils.RefCount;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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

    @Resource
    private UserDao userDao;

    @SuppressWarnings("Duplicates")
    @Override
    public List<Employee> getEmployees(RefCount refCount, String name, String dept
            , Integer pageIndex, Integer pageSize) {
        return employeeDao.search(refCount, name, dept, pageIndex, pageSize);
    }

    @Override
    public List<Employee> getDeletedEmployees() {
        return employeeDao.getDeleted();
    }

    @Override
    public Employee addEmployee(Employee employee) {
        String id = employeeDao.save(employee);
        employee.setId(id);
        return employee;
    }

    @Override
    public boolean recover(String id) {
        return employeeDao.recover(id);
    }

    @Override
    public ServerResponse<String> relateUser(String username, String password, String empId) {
        if (!userDao.exists(username)) {
            return ServerResponse.createByErrorMessage("用户名不存在");
        }
        String md5Password = MD5Util.getMD5(password);
        User user = userDao.getUserByUsernameAndPassword(username, md5Password);
        if (user == null) {
            return ServerResponse.createByErrorMessage("密码错误");
        }
        if (employeeDao.isRelated(user)) {
            return ServerResponse.createByErrorMessage("该账号已经与员工关联!");
        }
        Employee employee = employeeDao.getEmployeeById(empId);
        if (employee == null) {
            return ServerResponse.createByErrorMessage("员工不存在");
        }
        if (employeeDao.isRelated(employee)) {
            return ServerResponse.createByErrorMessage("该员工已有关联账号");
        }
        if (employeeDao.relateUser(employee, user)) {
            return ServerResponse.createBySuccess();
        } else {
            return ServerResponse.createByErrorMessage("关联失败");
        }
    }

    @Override
    public void unrelateUser(String id) {
        Employee employee = employeeDao.getEmployeeById(id);
        employee.setUser(null);
        employeeDao.update(employee);
    }

    @Override
    public long countEmployee() {
        return employeeDao.count();
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
