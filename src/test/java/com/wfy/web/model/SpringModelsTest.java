package com.wfy.web.model;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.FlushModeType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/7/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml",
        "classpath:hibernate-config.xml" })
@Rollback
@Transactional(transactionManager = "transactionManager")
public class SpringModelsTest extends AbstractJUnit4SpringContextTests{

    @Autowired
    public HibernateTemplate hibernateTemplate;

    @Autowired
    private SessionFactory sessionFactory;

    @Before
    public void myInitMethod(){
        sessionFactory.getCurrentSession().setFlushMode(FlushModeType.AUTO);
    }

    @Test
    public void testRoleUser() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Role role = new Role("admi3471232112332n",RoleStatus.ONLINE);
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        User user = new User("asd1314732123","123123",UserStatus.ONLINE);
        user.setRoles(roles);
        user.setCreateTime(new Date());
        user.setLastLoginTime(new Date());
        user.setStatus(UserStatus.ONLINE);

        session.save(user);
        session.getTransaction().commit();
    }

    @Test
    public void testMenu() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Role role = new Role("admin",RoleStatus.ONLINE);
        Menu topMenu = new Menu("a",1);
        List<Role> topMenuRoles = new ArrayList<>();
        topMenuRoles.add(role);
        Menu subMenu1 = new Menu("a1",topMenu,1);
        Menu subMenu2 = new Menu("a2", topMenu,2);
        List<Role> subMenu1Roles = new ArrayList<>();
        subMenu1Roles.add(role);
        List<Role> subMenu2Roles = new ArrayList<>();
        subMenu2Roles.add(role);
        topMenu.setRoles(topMenuRoles);
        subMenu1.setRoles(subMenu1Roles);
        subMenu2.setRoles(subMenu2Roles);
        List<Menu> children = new ArrayList<>();
        children.add(subMenu1);
        children.add(subMenu2);
        topMenu.setChildren(children);
        session.save(topMenu);
        session.getTransaction().commit();
    }
}
