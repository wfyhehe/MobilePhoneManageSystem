package com.wfy.web.dao;

import com.wfy.web.model.Employee;
import com.wfy.web.model.User;
import com.wfy.web.utils.CloneUtil;
import com.wfy.web.utils.RefCount;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/8/26.
 */

@Repository
public class EmployeeDao {

    @Resource
    private HibernateTemplate hibernateTemplate;

    private List<Employee> normalizeEmployees(List<Employee> employees) {
        for (Employee employee : employees) {
            normalizeEmployee(employee);
        }
        return employees;
    }

    private Employee normalizeEmployee(Employee employee) {
        if (employee != null
                && employee.getUser() != null
                && employee.getUser().getEmployee() != null
                && employee.getUser().getEmployee().equals(employee)) {
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


    public List<Employee> search(RefCount refCount, String name, String dept, Integer pageIndex,
                                 Integer pageSize) {
        List<Employee> employees;
        DetachedCriteria criteria = DetachedCriteria.forClass(Employee.class, "e")
                .setFetchMode("user", FetchMode.SELECT)
                .add(Restrictions.ne("e.deleted", true))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        if (StringUtils.isNotBlank(name)) {
            criteria.add(Restrictions.like("e.name", "%" + name + "%"));
        }
        if (StringUtils.isNotBlank(dept)) {
            criteria.createAlias("dept", "d")
                    .add(Restrictions.eq("d.name", dept));
        }
        DetachedCriteria countCriteria = CloneUtil.clone(criteria);
        countCriteria.setProjection(Projections.rowCount());
        long count = ((List<Long>) hibernateTemplate.findByCriteria(countCriteria)).get(0);
        refCount.setCount(count);
        if (pageIndex != null && pageSize != null) {
            int offset = (pageIndex - 1) * pageSize;
            employees = (List<Employee>) hibernateTemplate.findByCriteria(criteria, offset, pageSize);
        } else {
            employees = (List<Employee>) hibernateTemplate.findByCriteria(criteria);
        }
        return normalizeEmployees(employees);
    }

    public List<Employee> getDeleted() {
        String hql = "from Employee e where e.deleted = 1 order by e.id";
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

    public long count() {
        String hql = "select count(*) from Employee e where e.deleted <> 1";
        List<Long> list = (List<Long>) hibernateTemplate.find(hql);
        return list.get(0);
    }

}
