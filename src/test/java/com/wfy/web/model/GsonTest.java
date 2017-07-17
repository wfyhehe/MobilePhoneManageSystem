package com.wfy.web.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/7/16.
 */
public class GsonTest {

    @Test
    public void test1() {
        User user = new User("wfyhehe", "123456", UserStatus.ONLINE);
        user.setRemark("a very clever guy");
        user.setId(118);
        user.setCreateTime(new Date());
        user.setLastLoginTime(new Date());
        Role role = new Role("idiot", RoleStatus.ONLINE);
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);
//        Gson gson = new Gson();
//        System.out.println(gson.toJson(user));
    }

    @Test
    public void test2() {
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
//        Gson gson = new GsonBuilder()
//                .excludeFieldsWithoutExposeAnnotation()
//                .create();
//        System.out.println(topMenu);
//        System.out.println(gson.toJson(topMenu));
    }
}
