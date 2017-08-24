package com.wfy.web.controller;

import com.wfy.web.common.ServerResponse;
import com.wfy.web.model.Role;
import com.wfy.web.service.IRoleService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Set;

/**
 * Created by Administrator on 2017/8/20.
 */
@CrossOrigin //TODO 开发使用，发布时必须取消
@RestController
@RequestMapping("/role/")
public class RoleController {

    @Resource
    IRoleService iRoleService;

    @RequestMapping(value = "get_roles", method = RequestMethod.GET)
    public ServerResponse<Set<Role>> getRoles() {
        Set roles = iRoleService.getRoles();
        if (roles.size() > 0) {
            return ServerResponse.createBySuccess(roles);
        } else {
            return ServerResponse.createByErrorMessage("获取角色失败");
        }
    }
}
