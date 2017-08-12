package com.wfy.web.model;

import com.wfy.web.dao.MenuDao;
import com.wfy.web.service.IUserService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/7/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml",
        "classpath:hibernate-config.xml"})
@Rollback
@Transactional(transactionManager = "transactionManager")
public class SpringModelsTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Autowired
    private MenuDao menuDao;

    @Autowired
    @Qualifier("iUserService")
    private IUserService iUserService;

    //    @Before
//    public void myInitMethod(){
//        sessionFactory.getCurrentSession().setFlushMode(FlushModeType.AUTO);
//    }
    @Autowired
    private SessionFactory sessionFactory;

    @Test
    public void testRoleUser() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Role role = new Role("admi3478631232112332n", RoleStatus.ONLINE);
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        User user = new User("asd1314738882123", "123123", UserStatus.ONLINE);
        user.setRoles(roles);
        user.setCreateTime(new Date());
        user.setLastLoginTime(new Date());
        user.setStatus(UserStatus.ONLINE);

        session.save(user);
        session.getTransaction().commit();
        session.close();
    }

    @Test
    public void testEmployee() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();


        User user = new User("asd1314732123", "123123", UserStatus.ONLINE);
        user.setCreateTime(new Date());
        user.setLastLoginTime(new Date());
        user.setStatus(UserStatus.ONLINE);

        Employee employee = new Employee("aaa", EmployeeType.SALES);
        employee.setUser(user);
        List<Employee> employees = new ArrayList<>();
        employees.add(employee);

        Department department = new Department("ha", "peking");
        department.setEmployees(employees);

        employee.setDepartment(department);

        session.save(department);
        session.save(user);
        session.save(employee);
        session.getTransaction().commit();
        session.close();
    }

    @Test
    public void testMenu() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Role role = new Role("asshole", RoleStatus.ONLINE);
        Menu topMenu = new Menu("b", 2);
        List<Role> topMenuRoles = new ArrayList<>();
        topMenuRoles.add(role);
        Menu subMenu1 = new Menu("b1", topMenu, 1);
        Menu subMenu2 = new Menu("b2", topMenu, 2);
        Menu subMenu3 = new Menu("b3", topMenu, 3);
        List<Role> subMenu1Roles = new ArrayList<>();
        subMenu1Roles.add(role);
        List<Role> subMenu2Roles = new ArrayList<>();
        subMenu2Roles.add(role);
        List<Role> subMenu3Roles = new ArrayList<>();
        subMenu3Roles.add(role);
        topMenu.setRoles(topMenuRoles);
        subMenu1.setRoles(subMenu1Roles);
        subMenu2.setRoles(subMenu2Roles);
        subMenu3.setRoles(subMenu3Roles);
        List<Menu> children = new ArrayList<>();
        children.add(subMenu1);
        children.add(subMenu2);
        children.add(subMenu3);
        topMenu.setChildren(children);
        session.save(topMenu);
        session.getTransaction().commit();
        session.close();
    }

    @Test
    public void testMenuDao() {
        Menu menu = new Menu();
        menu.setId(String.valueOf(6));
        List<Role> roles = new ArrayList<>();
        Role r1 = new Role();
        r1.setId(String.valueOf(7));
        roles.add(r1);
        Role r2 = new Role();
        r2.setId(String.valueOf(12));
        roles.add(r2);
        menu.setRoles(roles);
        List list = menuDao.getMenus(menu, roles);
        System.out.println(list);
    }

    @Test
    public void testUser() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        User user = new User("admin", "admin", UserStatus.ONLINE);
        user.setCreateTime(new Date());
        user.setLastLoginTime(new Date());

        session.save(user);
        session.getTransaction().commit();
        session.close();
    }

    @Test
    public void testInsert() {
        User user = new User("admin", "admin", UserStatus.ONLINE);
        user.setCreateTime(new Date());
        user.setLastLoginTime(new Date());

        iUserService.register(user);
    }
}
