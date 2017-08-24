package com.wfy.web.service;

import com.wfy.web.model.Role;

import java.util.Set;

/**
 * Created by Administrator on 2017/8/22.
 */
public interface IRoleService {

    Set<Role> getRoles();

    Role getRoleByName(String name);
}
