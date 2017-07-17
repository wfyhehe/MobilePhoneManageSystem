package com.wfy.web.service;

import com.wfy.web.dao.MenuDao;
import com.wfy.web.model.Menu;
import com.wfy.web.model.Role;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/16.
 */
@Service
@Transactional
public class MenuService {
    private MenuDao menuDao;

    public MenuDao getMenuDao() {
        return menuDao;
    }

    @Resource
    public void setMenuDao(MenuDao menuDao) {
        this.menuDao = menuDao;
    }

    public List<Menu> getMenus(Menu parentMenu, List<Role> roles) {
        List<Menu> menus = menuDao.getMenus(parentMenu, roles);
//        由于设置了fetch为eager，自动取出所有孩子，不需要手动递归
//        if (menus.size() > 0) {
//            for (Menu menu : menus) {
//                menu.setChildren(getMenus(menu, menu.getRoles()));
//            }
//        }
        return menus;

    }
}
