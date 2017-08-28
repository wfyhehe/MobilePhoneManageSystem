package com.wfy.web.service.impl;

import com.wfy.web.dao.MenuDao;
import com.wfy.web.dao.RoleDao;
import com.wfy.web.model.Menu;
import com.wfy.web.model.Role;
import com.wfy.web.service.IMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2017/7/16.
 */
@Service("iMenuService")
@Transactional
public class MenuServiceImpl implements IMenuService {

    @Resource
    private MenuDao menuDao;

    @Resource
    private RoleDao roleDao;

    @Override
    public List<Menu> getMenus(Menu parentMenu, Set<Role> roles) {
        List<Menu> menus = menuDao.getMenus(parentMenu, roles);
//        由于设置了fetch为eager，自动取出所有孩子，不需要手动递归
//        if (menus.size() > 0) {
//            for (Menu menu : menus) {
//                menu.setChildren(getMenus(menu, menu.getRoles()));
//            }
//        }
        return menus;
    }

    public Menu getMenu(String id) {
        return menuDao.getMenuById(id);
    }

    @Override
    public List<Menu> getTopMenus(List<Role> roles) {
        List<Menu> menus = menuDao.getTopMenus(roles);
        return menus;
    }

    @Override
    public Menu addMenu(String parentId, String name, int sortOrder) {
        Menu menu;
        if (parentId != null) {
            Menu parent = menuDao.getMenuById(parentId);
            menu = new Menu(name, parent, null, sortOrder);
        } else {
            menu = new Menu(name, sortOrder);
        }
        Set<Role> set = new HashSet<>();
        Role role = roleDao.getRoleByName("超级管理员");
        set.add(role);
        menu.setRoles(set);
        String id = menuDao.save(menu);
        menu.setId(id);
        return menu;
    }

    @Override
    public void update(Menu menu) {
        Menu oldMenu = menuDao.getMenuById(menu.getId());
        if (menu.getName() != null) {
            oldMenu.setName(menu.getName());
        }
        if (menu.getChildren() != null) {
            oldMenu.setChildren(menu.getChildren());
        }
        if (menu.getRoles() != null) {
            oldMenu.setRoles(menu.getRoles());
        }
        if (menu.getSortOrder() != null) {
            oldMenu.setSortOrder(menu.getSortOrder());
        }
        if (menu.getType() != null) {
            oldMenu.setType(menu.getType());
        }
        if (menu.getActions() != null) {
            oldMenu.setActions(menu.getActions());
        }
        if (menu.getParent() != null) {
            oldMenu.setParent(menu.getParent());
        }
        if (menu.getPath() != null) {
            oldMenu.setPath(menu.getPath());
        }
        if (menu.getRemark() != null) {
            oldMenu.setRemark(menu.getRemark());
        }
        menuDao.update(oldMenu);
    }

    @Override
    public void delete(String id) {
        Menu menu = menuDao.getMenuById(id);
        menuDao.delete(menu);
    }

    @Override
    public boolean up(String id) {
        Menu menu = menuDao.getMenuById(id);
        Menu preMenu = menuDao.getPre(menu);
        if (preMenu == null) {
            return false;
        }
        int temp;
        temp = menu.getSortOrder();
        menu.setSortOrder(preMenu.getSortOrder());
        preMenu.setSortOrder(temp);

        menuDao.update(menu);
        menuDao.update(preMenu);
        return true;
    }

    @Override
    public boolean down(String id) {
        Menu menu = menuDao.getMenuById(id);
        Menu sucMenu = menuDao.getSuc(menu);
        if (sucMenu == null) {
            return false;
        }
        int temp;
        temp = menu.getSortOrder();
        menu.setSortOrder(sucMenu.getSortOrder());
        sucMenu.setSortOrder(temp);

        menuDao.update(menu);
        menuDao.update(sucMenu);
        return true;
    }
}
