package com.wfy.web.service.impl;

import com.wfy.web.dao.RoleDao;
import com.wfy.web.model.Role;
import com.wfy.web.service.IRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
    public Set<Role> getRoles() {
        return roleDao.getAll();
    }

    @Override
    public Role getRoleByName(String name) {
        return roleDao.getRoleByName(name);
    }
}
