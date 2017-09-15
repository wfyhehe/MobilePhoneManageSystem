package com.wfy.web.model;

import com.wfy.web.model.enums.RoleStatus;
import com.wfy.web.model.enums.UserStatus;
import org.junit.Test;

import java.util.*;

/**
 * Created by Administrator on 2017/7/16.
 */
public class GsonTest {

    @Test
    public void test1() {
        User user = new User("wfyhehe", "123456", UserStatus.ONLINE);
        user.setRemark("a very clever guy");
        user.setId(String.valueOf(118));
        user.setCreateTime(new Date());
        user.setLastLoginTime(new Date());
        Role role = new Role("idiot", RoleStatus.ONLINE);
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
//        Gson gson = new Gson();
//        System.out.println(gson.toJson(user));
    }
}
