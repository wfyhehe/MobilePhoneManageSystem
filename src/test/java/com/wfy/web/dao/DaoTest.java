package com.wfy.web.dao;

import com.wfy.web.model.Menu;
import com.wfy.web.model.User;
import com.wfy.web.service.IMenuService;
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

/**
 * Created by Administrator on 2017/8/20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml",
        "classpath:hibernate-config.xml", "classpath:shiro-config.xml"})
@Rollback
@Transactional(transactionManager = "transactionManager")
public class DaoTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    @Qualifier("iMenuService")
    private IMenuService iMenuService;

    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Test
    public void upTest() {
//        Session session = sessionFactory.openSession();
//        session.beginTransaction();
//
        Menu menu = menuDao.getMenuByName("菜单管理");
        System.out.println(menuDao.getPre(menu));

//        session.getTransaction().commit();
//        session.close();
    }

    @Test
    public void isRelatedTest() {
        User user = userDao.getUserByName("admin");
        System.out.println(user);
        System.out.println(employeeDao.isRelated(user));
    }

    @Test
    public void hqlTest() {
        User user = userDao.getUserByName("admin");
        String hql = "select count(*) from Employee e where e.user.id = ?";
        System.out.println(hibernateTemplate.find(hql, user.getId()));
    }

}
