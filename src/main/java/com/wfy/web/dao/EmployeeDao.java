package com.wfy.web.dao;

import com.wfy.web.model.Employee;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Administrator on 2017/8/26.
 */

@Repository
public class EmployeeDao {

    @Resource
    private HibernateTemplate hibernateTemplate;

    private void normalizeEmployees(List<Employee> employees) {
        employees.sort(Comparator.comparingInt(o -> o.getId().hashCode()));
        for (Employee employee : employees) {
            normalizeEmployee(employee);
        }
    }

    private void normalizeEmployee(Employee employee) {
        if (employee.getUser() != null && employee.getUser().getEmployee().equals(employee)) {
            hibernateTemplate.evict(employee);
            employee.getUser().setEmployee(null); // 从属的user的employee属性设为空,防止无限递归
        }
    }

    public List<Employee> getAll() {
        String hql = "from Employee e where e.deleted <> 1";
        List<Employee> employees = (List<Employee>) hibernateTemplate.find(hql);
        normalizeEmployees(employees);
        return employees;
    }

    public List<Employee> search(String name, String dept) {
        name = "%" + name + "%";
        dept = "%" + dept + "%";
        String hql = "from Employee e where e.name like ? and e.dept.id in (" +
                "select d.id from Dept d where d.name like ? and d.deleted <> 1" +
                ") and e.deleted <> 1";
        List<Employee> employees = (List<Employee>) hibernateTemplate.find(hql, name, dept);
        System.out.println(employees);
        normalizeEmployees(employees);
        return employees;
    }

    public List<Employee> search(String key) {
        key = "%" + key + "%";
        System.out.println(key);
        String hql = "from Employee e where e.name like ? and e.deleted <> 1";
        List<Employee> employees = (List<Employee>) hibernateTemplate.find(hql, key);
        normalizeEmployees(employees);
        return employees;
    }

    public List<Employee> getDeleted() {
        String hql = "from Employee e where e.deleted = 1";
        List<Employee> employees = (List<Employee>) hibernateTemplate.find(hql);
        normalizeEmployees(employees);
        return employees;
    }

    public Employee getEmployeeByName(String name) {
        String hql = "from Employee e where e.name = ? and e.deleted <> 1";
        List<Employee> list = (List<Employee>) hibernateTemplate.find(hql, name);
        if (list.size() > 0) {
            Employee employee = list.get(0);
            normalizeEmployee(employee);
            return employee;
        } else {
            return null;
        }
    }

    public Employee getEmployeeById(String id) {
        String hql = "from Employee e where e.id = ? and e.deleted <> 1";
        List<Employee> list = (List<Employee>) hibernateTemplate.find(hql, id);
        if (list.size() > 0) {
            Employee employee = list.get(0);
            normalizeEmployee(employee);
            return employee;
        } else {
            return null;
        }
    }

    public void update(Employee employee) {
        hibernateTemplate.update(employee);
    }


    public String save(Employee employee) {
        return (String) hibernateTemplate.save(employee);
    }

    public boolean recover(String id) {
        String hql = "from Employee e where e.id = ? and e.deleted = 1";
        List<Employee> list = (List<Employee>) hibernateTemplate.find(hql, id);
        if (list.size() <= 0) {
            return false;
        }
        Employee employee = list.get(0);
        employee.setDeleted(false);
        return true;
    }


}
