package com.wfy.web.service;

import com.wfy.web.dto.MenuRoleDto;
import com.wfy.web.model.Menu;

import java.util.List;

/**
 * Created by Administrator on 2017/8/18.
 */
public interface IMenuService {

    List<Menu> getMenus(Menu parentMenu);

    List<Menu> getTopMenus();

    Menu getMenu(String id);

    Menu addMenu(String parentId, String name, int sortOrder);

    void updateFromRole(MenuRoleDto roleDto) throws Exception;

    boolean up(String id);

    boolean down(String id);

    void update(Menu menu);

    void delete(String id);
}
