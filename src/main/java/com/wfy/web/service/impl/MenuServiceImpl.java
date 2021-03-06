package com.wfy.web.service.impl;

import com.wfy.web.dao.MenuDao;
import com.wfy.web.dao.RoleDao;
import com.wfy.web.dto.MenuRoleDto;
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
    public List<Menu> getMenus(Menu parentMenu) {
        List<Menu> menus = menuDao.getMenus(parentMenu);
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
    public List<Menu> getTopMenus() {
        List<Menu> menus = menuDao.getTopMenus();
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
//        List<Role> list = new ArrayList<>();
//        Role role = roleDao.getRoleByName("超级管理员");
//        list.add(role);
        menu.setRoles(set);
        String id = menuDao.save(menu);
        menu.setId(id);
        return menu;
    }

    @Override
    public void update(Menu menu) {
        Menu oldMenu = menuDao.getMenuById(menu.getId());
        if (menu.getName() == null) {
            menu.setName(oldMenu.getName());
        }
        if (menu.getChildren() == null) {
            menu.setChildren(oldMenu.getChildren());
        }
        if (menu.getRoles() == null) {
            menu.setRoles(oldMenu.getRoles());
        }
        if (menu.getSortOrder() == null) {
            menu.setSortOrder(oldMenu.getSortOrder());
        }
        if (menu.getType() == null) {
            menu.setType(oldMenu.getType());
        }
        if (menu.getActions() == null) {
            menu.setActions(oldMenu.getActions());
        }
        if (menu.getParent() == null) {
            menu.setParent(oldMenu.getParent());
        }
        if (menu.getPath() == null) {
            menu.setPath(oldMenu.getPath());
        }
        if (menu.getRemark() == null) {
            menu.setRemark(oldMenu.getRemark());
        }
        menuDao.merge(menu);
    }

    @Override
    public void updateFromRole(MenuRoleDto roleDto) {
        Role role = roleDto.getRole();
        List<Menu> menus = menuDao.getMenus();
        for (Menu menu : menus) {
            if (roleDto.getMenuIds().contains(menu.getId())) {
                if (!menu.getRoles().contains(role)) {
                    menu.getRoles().add(role);
                }
            } else {
                menu.getRoles().remove(role);
            }
            menuDao.merge(menu);
        }
    }

    @Override
    public void delete(String id) {
        Menu menu = menuDao.getMenuById(id);
//        if (menu.getParent() != null) {
//            List<Menu> siblings = menu.getParent().getChildren();
//            for (int i = 0; i < siblings.size(); i++) {
//                Menu sibling = siblings.get(i);
//                if (sibling.getId().equals(id)) {
//                    siblings.remove(i);
//                }
//            }
//            System.out.println(menu.getParent());
//        }
//        System.out.println(menu);
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
