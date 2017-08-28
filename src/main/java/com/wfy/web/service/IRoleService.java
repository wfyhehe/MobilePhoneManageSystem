package com.wfy.web.service;

import com.wfy.web.model.Menu;
import com.wfy.web.model.Role;

import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2017/8/22.
 */
public interface IRoleService {

    List<Role> getRoles();

    Role getRoleByName(String name);

    void updateRole(Role role);

    Role getRoleById(String id);

    boolean delete(String id);

    List<Role> getDeletedRoles();

    Role addRole(String name);

    boolean recover(String id);
}
