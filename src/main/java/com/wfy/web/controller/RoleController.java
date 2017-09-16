package com.wfy.web.controller;

import com.wfy.web.common.ServerResponse;
import com.wfy.web.model.Role;
import com.wfy.web.model.enums.RoleStatus;
import com.wfy.web.service.IRoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/20.
 */
//@CrossOrigin //TODO 开发使用，发布时必须取消
@RestController
@RequestMapping("/role/")
public class RoleController {

    @Resource
    private IRoleService iRoleService;

    @RequestMapping(value = "get_roles.do", method = RequestMethod.GET)
    public ServerResponse<List<Role>> getRoles() {
        List roles = iRoleService.getRoles();
        if (roles != null) {
            return ServerResponse.createBySuccess(roles);
        } else {
            return ServerResponse.createByErrorMessage("获取角色失败");
        }
    }

    @RequestMapping(value = "add_role.do", method = RequestMethod.POST)
    public ServerResponse<Role> addRole(@RequestBody Map<String, Object> roleMap) {
        String name = (String) roleMap.get("name");
        String statusStr = (String) roleMap.get("status");
        String remark = (String) roleMap.get("remark");
        Role role = new Role();
        role.setName(name);
        role.setRemark(remark);
        switch (statusStr) {
            case "ONLINE": {
                role.setStatus(RoleStatus.ONLINE);
                break;
            }
            case "OFFLINE": {
                role.setStatus(RoleStatus.OFFLINE);
                break;
            }
            case "DELETED": {
                role.setStatus(RoleStatus.DELETED);
                break;
            }
        }
        try {
            role = iRoleService.addRole(role);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage(e.getMessage());
        }
        return ServerResponse.createBySuccess(role);
    }

    @RequestMapping(value = "get_deleted_roles.do", method = RequestMethod.GET)
    public ServerResponse<List<Role>> getDeletedRoles() {
        List roles = iRoleService.getDeletedRoles();
        if (roles != null) {
            return ServerResponse.createBySuccess(roles);
        } else {
            return ServerResponse.createByErrorMessage("获取角色失败");
        }
    }

    @RequestMapping(value = "recover_role.do", method = RequestMethod.GET)
    public ServerResponse<String> recoverRole(String id) {
        if (iRoleService.recover(id)) {
            return ServerResponse.createBySuccess();
        } else {
            return ServerResponse.createByErrorMessage("恢复失败");
        }
    }

    @RequestMapping(value = "get_role.do", method = RequestMethod.GET)
    public ServerResponse<Role> getRole(String id) {
        Role role = iRoleService.getRoleById(id);
        if (role != null) {
            return ServerResponse.createBySuccess(role);
        } else {
            return ServerResponse.createByErrorMessage("获取角色失败");
        }
    }

    @RequestMapping(value = "update_role.do", method = RequestMethod.POST)
    public ServerResponse<String> updateRole(@RequestBody Role role) {
        try {
            iRoleService.updateRole(role);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("更新失败");
        }
        return ServerResponse.createBySuccess();
    }

    @RequestMapping(value = "delete_role.do", method = RequestMethod.GET)
    public ServerResponse<String> delete(String id) {
        if (iRoleService.delete(id)) {
            return ServerResponse.createBySuccess();
        } else {
            return ServerResponse.createByErrorMessage("删除失败");
        }
    }
}
