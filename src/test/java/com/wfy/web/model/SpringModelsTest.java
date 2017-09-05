package com.wfy.web.model;

import com.wfy.web.dao.MenuDao;
import com.wfy.web.model.enums.ActionType;
import com.wfy.web.model.enums.EmployeeType;
import com.wfy.web.model.enums.RoleStatus;
import com.wfy.web.model.enums.UserStatus;
import com.wfy.web.service.IUserService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
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

import java.util.*;

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

        Dept dept = new Dept("ha", "peking");
        dept.setEmployees(employees);

        employee.setDept(dept);

        session.save(dept);
        session.save(user);
        session.save(employee);
        session.getTransaction().commit();
        session.close();
    }

    @Test
    public void testMenu() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Role role = new Role("superadmin", RoleStatus.ONLINE);
        Menu topMenu1 = new Menu("系统管理", 2);
        Set<Role> topMenuRoles = new HashSet<>();
        topMenuRoles.add(role);
        Menu subMenu1 = new Menu("用户管理", topMenu1, "user_manage", 1);
        Menu subMenu2 = new Menu("权限管理", topMenu1, "auth_manage", 2);
        Menu subMenu3 = new Menu("菜单管理", topMenu1, "menu_manage", 3);
        Action action1 = new Action("添加菜单", "/menu/add_menu.do", ActionType.GRANTED, subMenu1);

        Set<Role> subMenu1Roles = new HashSet<>();
        subMenu1Roles.add(role);
        Set<Role> subMenu2Roles = new HashSet<>();
        subMenu2Roles.add(role);
        Set<Role> subMenu3Roles = new HashSet<>();
        subMenu3Roles.add(role);

        topMenu1.setRoles(topMenuRoles);
        subMenu1.setRoles(subMenu1Roles);
        subMenu2.setRoles(subMenu2Roles);
        subMenu3.setRoles(subMenu3Roles);

        List<Menu> children = new ArrayList<>();
        children.add(subMenu1);
        children.add(subMenu2);
        children.add(subMenu3);
        topMenu1.setChildren(children);

        session.save(topMenu1);
        session.save(action1);
        session.getTransaction().commit();
        session.close();
    }

    @Test
    public void testMenuDao() {
        Menu menu = new Menu();
        menu.setId(String.valueOf(6));
        Set<Role> roles = new HashSet<>();
        Role r1 = new Role();
        r1.setId(String.valueOf(7));
        roles.add(r1);
        Role r2 = new Role();
        r2.setId(String.valueOf(12));
        roles.add(r2);
        menu.setRoles(roles);
        List list = menuDao.getMenus(menu);
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

    @Test
    public void testRole() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Role role1 = new Role("店长", RoleStatus.ONLINE);
        Role role2 = new Role("商务经理", RoleStatus.ONLINE);
        Role role3 = new Role("财务人员", RoleStatus.ONLINE);

        session.save(role1);
        session.save(role2);
        session.save(role3);
        session.getTransaction().commit();
        session.close();
    }

    @Test
    public void testHql() {
        String hql = "select count(*) from MobileModel mm";
        System.out.println(hibernateTemplate.find(hql));
    }

    @Test
    public void testHql2() {
        String hql = "select count(*) from MobileInbound mi where mi.id = ?";
        List<Long> list = (List<Long>) hibernateTemplate.find(hql, "11111111");
        System.out.println(list);
    }

    @Test
    public void testCriteria() {
        DetachedCriteria criteria = DetachedCriteria.forClass(Employee.class, "e")
                .add(Restrictions.ne("e.deleted", true));
//                .createAlias("dept", "d")
//                .add(Restrictions.ne("d.deleted", true))
//                .add(Restrictions.eq("d.name", "Pineapple"))
//                .add(Restrictions.like("e.name", "%%"))
//                .addOrder(Order.asc("e.id"));
        List<Employee> employees =
                (List<Employee>) hibernateTemplate.findByCriteria(criteria);
        for (Employee employee : employees) {
            System.out.println(employee.getName() + ",dept: " + employee.getDept());
        }
//        criteria.setProjection(Projections.rowCount());
//        System.out.println(hibernateTemplate.findByCriteria(criteria).get(0));
    }
}
