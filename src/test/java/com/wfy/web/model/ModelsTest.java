package com.wfy.web.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

/**
 * Created by Administrator on 2017/7/16.
 */
public class ModelsTest {
    private SessionFactory sessionFactory;

    @Before
    public void setUp() throws Exception {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    @After
    public void tearDown() throws Exception {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    @Test
    public void test() {


        User user = new User();
        user.setCreateTime(new Date());
        user.setLastLoginTime(new Date());
        user.setId(15);
        user.setPassword("123456");
        user.setRemark("haha");
        user.setUsername("CJ");
        user.setStatus(UserStatus.ONLINE);
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        session.save(user);

        session.getTransaction().commit();
        System.out.println(user);
    }
}
