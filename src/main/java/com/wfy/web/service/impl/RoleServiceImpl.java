package com.wfy.web.service.impl;

import com.wfy.web.dao.RoleDao;
import com.wfy.web.model.Role;
import com.wfy.web.model.RoleStatus;
import com.wfy.web.service.IRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2017/8/22.
 */
@Service("iRoleService")
@Transactional
public class RoleServiceImpl implements IRoleService {

    @Resource
    RoleDao roleDao;

    @Override
    public List<Role> getRoles() {
        return roleDao.getAll();
    }

    @Override
    public List<Role> getDeletedRoles() {
        return roleDao.getDeleted();
    }

    @Override
    public Role addRole(String name) {
        Role role = new Role();
        role.setStatus(RoleStatus.ONLINE);
        role.setName(name);
        String id = roleDao.save(role);
        role.setId(id);
        return role;
    }

    @Override
    public boolean recover(String id) {
        return roleDao.recover(id);
    }

    @Override
    public void updateRole(Role role) {
        roleDao.update(role);
    }

    @Override
    public Role getRoleById(String id) {
        return roleDao.getRoleById(id);
    }

    @Override
    public Role getRoleByName(String name) {
        return roleDao.getRoleByName(name);
    }

    @Override
    public boolean delete(String id) {
        Role role = roleDao.getRoleById(id);
        if(role == null) {
            return false;
        }
        role.setStatus(RoleStatus.DELETED);
        roleDao.update(role);
        return true;
    }




}
