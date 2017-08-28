package com.wfy.web.controller;

import com.wfy.web.common.ServerResponse;
import com.wfy.web.model.Employee;
import com.wfy.web.model.EmployeeType;
import com.wfy.web.service.IDeptService;
import com.wfy.web.service.IEmployeeService;
import com.wfy.web.service.IUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/26.
 */
@CrossOrigin //TODO 开发使用，发布时必须取消
@RestController
@RequestMapping("/employee/")
public class EmployeeController {

    @Resource
    private IEmployeeService iEmployeeService;

    @Resource
    private IDeptService iDeptService;

    @Resource
    private IUserService iUserService;

    @RequestMapping(value = "get_employees.do", method = RequestMethod.POST)
    public ServerResponse<List<Employee>> getEmployees(@RequestBody Map<String, Object> map) {
        String name = (String) map.get("name");
        String dept = (String) map.get("dept");
        List<Employee> employees = iEmployeeService.getEmployees(name, dept);
        if (employees != null) {
            return ServerResponse.createBySuccess(employees);
        } else {
            return ServerResponse.createByErrorMessage("获取员工失败");
        }
    }

    @RequestMapping(value = "add_employee.do", method = RequestMethod.POST)
    public ServerResponse<Employee> addMenu(@RequestBody Map<String, Object> nameMap) {
        String name = (String) nameMap.get("name");
        Employee employee;
        try {
            employee = iEmployeeService.addEmployee(name);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage(e.getMessage());
        }
        return ServerResponse.createBySuccess(employee);
    }

    @RequestMapping(value = "get_deleted_employees.do", method = RequestMethod.GET)
    public ServerResponse<List<Employee>> getDeletedEmployees() {
        List<Employee> employees = iEmployeeService.getDeletedEmployees();
        if (employees != null) {
            return ServerResponse.createBySuccess(employees);
        } else {
            return ServerResponse.createByErrorMessage("获取员工失败");
        }
    }

    @RequestMapping(value = "recover_employee.do", method = RequestMethod.GET)
    public ServerResponse<String> recoverEmployee(String id) {
        if (iEmployeeService.recover(id)) {
            return ServerResponse.createBySuccess();
        } else {
            return ServerResponse.createByErrorMessage("恢复失败");
        }
    }

    @RequestMapping(value = "get_employee.do", method = RequestMethod.GET)
    public ServerResponse<Employee> getEmployee(String id) {
        Employee employee = iEmployeeService.getEmployeeById(id);
        if (employee != null) {
            return ServerResponse.createBySuccess(employee);
        } else {
            return ServerResponse.createByErrorMessage("获取员工失败");
        }
    }

    @RequestMapping(value = "update_employee.do", method = RequestMethod.POST)
    public ServerResponse<String> updateEmployee(@RequestBody Map<String, Object> employeeMap) {
        Employee employee = new Employee();
        String id = (String) employeeMap.get("id");
        String name = (String) employeeMap.get("name");
        String tel = (String) employeeMap.get("tel");
        String typeStr = (String) employeeMap.get("type");
        String deptStr = (String) employeeMap.get("dept");
        String remark = (String) employeeMap.get("remark");
        String userStr = (String) employeeMap.get("user");
        employee.setId(id);
        employee.setName(name);
        employee.setTel(tel);
        switch (typeStr) {
            case "SALES": {
                employee.setType(EmployeeType.SALES);
                break;
            }
            case "OTHER": {
                employee.setType(EmployeeType.OTHER);
                break;
            }
        }
        employee.setDept(iDeptService.getDeptByName(deptStr));
        employee.setRemark(remark);
        employee.setUser(iUserService.getUserByName(userStr));
        try {
            employee.setDeleted(false);
            iEmployeeService.updateEmployee(employee);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("更新失败");
        }
        return ServerResponse.createBySuccess();
    }

    @RequestMapping(value = "delete_employee.do", method = RequestMethod.GET)
    public ServerResponse<String> delete(String id) {
        if (iEmployeeService.delete(id)) {
            return ServerResponse.createBySuccess();
        } else {
            return ServerResponse.createByErrorMessage("删除失败");
        }
    }
}
