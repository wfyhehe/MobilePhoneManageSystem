package com.wfy.web.service;

import com.wfy.web.model.Menu;
import com.wfy.web.model.Role;

import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2017/8/18.
 */
public interface IMenuService {

    List<Menu> getMenus(Menu parentMenu, Set<Role> roles);

    List<Menu> getTopMenus(List<Role> roles);

    Menu getMenu(String id);

    Menu addMenu(String parentId, int sortOrder);

    void update(Menu menu);

    boolean up(String id);

    boolean down(String id);

    void delete(String id);
}
