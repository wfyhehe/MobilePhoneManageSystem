package com.wfy.web.service;

import com.wfy.web.model.Menu;
import com.wfy.web.model.Role;

import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2017/8/18.
 */
public interface IMenuService {

    List<Menu> getMenus(Menu parentMenu);

    List<Menu> getTopMenus();

    Menu getMenu(String id);

    Menu addMenu(String parentId, String name, int sortOrder);

    void update(Menu menu) throws Exception;

    boolean up(String id);

    boolean down(String id);

    void delete(String id);
}
