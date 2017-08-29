package com.wfy.web.dao;

import com.wfy.web.model.Employee;
import com.wfy.web.model.User;
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

    private List<Employee> normalizeEmployees(List<Employee> employees) {
        employees.sort(Comparator.comparingInt(o -> o.getId().hashCode()));
        for (Employee employee : employees) {
            normalizeEmployee(employee);
        }
        return employees;
    }

    private Employee normalizeEmployee(Employee employee) {
        if (employee.getUser() != null && employee.getUser().getEmployee().equals(employee)) {
            hibernateTemplate.evict(employee);
            employee.getUser().setEmployee(null); // 从属的user的employee属性设为空,防止无限递归
            hibernateTemplate.clear();
        }
        return employee;
    }

    private Employee extractAndNormalizeFirstEmployee(List<Employee> list) {
        if (list.size() > 0) {
            Employee employee = list.get(0);
            return normalizeEmployee(employee);
        } else {
            return null;
        }
    }

    public List<Employee> getAll() {
        String hql = "from Employee e where e.deleted <> 1";
        List<Employee> employees = (List<Employee>) hibernateTemplate.find(hql);
        return normalizeEmployees(employees);
    }

    public List<Employee> search(String name, String dept) {
        name = "%" + name + "%";
        dept = "%" + dept + "%";
        String hql = "from Employee e where e.name like ? and e.dept.id in (" +
                "select d.id from Dept d where d.name like ? and d.deleted <> 1" +
                ") and e.deleted <> 1";
        List<Employee> employees = (List<Employee>) hibernateTemplate.find(hql, name, dept);
        return normalizeEmployees(employees);
    }

    public List<Employee> search(String key) {
        key = "%" + key + "%";
        System.out.println(key);
        String hql = "from Employee e where e.name like ? and e.deleted <> 1";
        List<Employee> employees = (List<Employee>) hibernateTemplate.find(hql, key);
        return normalizeEmployees(employees);
    }

    public List<Employee> getDeleted() {
        String hql = "from Employee e where e.deleted = 1";
        List<Employee> employees = (List<Employee>) hibernateTemplate.find(hql);
        return normalizeEmployees(employees);
    }

    public Employee getEmployeeByName(String name) {
        String hql = "from Employee e where e.name = ? and e.deleted <> 1";
        List<Employee> list = (List<Employee>) hibernateTemplate.find(hql, name);
        return extractAndNormalizeFirstEmployee(list);
    }

    public Employee getEmployeeById(String id) {
        String hql = "from Employee e where e.id = ? and e.deleted <> 1";
        List<Employee> list = (List<Employee>) hibernateTemplate.find(hql, id);
        return extractAndNormalizeFirstEmployee(list);
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


    public boolean isRelated(User user) {
        String hql = "select count(*) from Employee e where e.user = ?";
        List<Long> list = (List<Long>) hibernateTemplate.find(hql, user);
        return list.get(0) > 0;
    }

    public boolean isRelated(Employee employee) {
        String hql = "select count(*) from Employee e where e.id = ? and e.user is not null";
        return ((List<Long>) hibernateTemplate.find(hql, employee.getId())).get(0) > 0;
    }

    public boolean relateUser(Employee employee, User user) {
        employee.setUser(user);
        hibernateTemplate.save(employee);
        return true;
    }


}
