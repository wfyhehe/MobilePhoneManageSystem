package com.wfy.web.controller;

import com.wfy.web.common.ServerResponse;
import com.wfy.web.model.Menu;
import com.wfy.web.model.Role;
import com.wfy.web.service.IMenuService;
import com.wfy.web.service.IRoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2017/8/18.
 */
@CrossOrigin //TODO 开发使用，发布时必须取消
@RestController
@RequestMapping("/menu/")
public class MenuController {

    @Resource
    private IMenuService iMenuService;

    @Resource
    private IRoleService iRoleService;

    @RequestMapping(value = "get_menus.do", method = RequestMethod.GET)
    public ServerResponse<List<Menu>> getMenus() {
        List<Menu> list = iMenuService.getTopMenus(null);
        if (list != null) {
            return ServerResponse.createBySuccess(list);
        } else {
            return ServerResponse.createByErrorMessage("获取菜单失败");
        }
    }

    @RequestMapping(value = "get_menu.do", method = RequestMethod.GET)
    public ServerResponse<Menu> getMenu(String id) {
        Menu menu = iMenuService.getMenu(id);
        if (menu == null) {
            return ServerResponse.createByErrorMessage("找不到此菜单项");
        } else {
            return ServerResponse.createBySuccess(menu);
        }
    }

    @RequestMapping(value = "add_menu.do", method = RequestMethod.POST)
    public ServerResponse<Menu> addMenu(@RequestBody Map<String, Object> menuMap) {
        String parentId = (String) menuMap.get("parentId");
        String name = (String) menuMap.get("name");
        int sortOrder = (int) menuMap.get("sortOrder");
        Menu menu;
        System.out.println(parentId);
        System.out.println(name);
        System.out.println(sortOrder);
        try {
            menu = iMenuService.addMenu(parentId, name, sortOrder);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage(e.getMessage());
        }
        return ServerResponse.createBySuccess(menu);
    }

    @RequestMapping(value = "update_menu.do", method = RequestMethod.POST)
    public ServerResponse<String> updateMenu(@RequestBody Map<String, Object> menuMap) {
        Menu menu = new Menu();
        String id = (String) menuMap.get("id");
        String name = (String) menuMap.get("name");
        String path = (String) menuMap.get("path");
        String remark = (String) menuMap.get("remark");
        Set<Role> roles = new HashSet<>();
        List<String> roleList = (List<String>) menuMap.get("roleNames");
        for (String roleName : roleList) {
            roles.add(iRoleService.getRoleByName(roleName));
        }
        menu.setId(id);
        menu.setName(name);
        menu.setPath(path);
        menu.setRemark(remark);
        menu.setRoles(roles);
        try {
            iMenuService.update(menu);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("更新失败");
        }
        return ServerResponse.createBySuccess();
    }

    @RequestMapping(value = "delete_menu.do", method = RequestMethod.GET)
    public ServerResponse<String> delete(String id) {
        try {
            iMenuService.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("删除失败");
        }
        return ServerResponse.createBySuccess();
    }

    @RequestMapping(value = "up.do", method = RequestMethod.GET)
    public ServerResponse<String> up(String id) {
        if (iMenuService.up(id)) {
            return ServerResponse.createBySuccess();
        } else {
            return ServerResponse.createByError();
        }
    }

    @RequestMapping(value = "down.do", method = RequestMethod.GET)
    public ServerResponse<String> down(String id) {
        if (iMenuService.down(id)) {
            return ServerResponse.createBySuccess();
        } else {
            return ServerResponse.createByError();
        }
    }

}
