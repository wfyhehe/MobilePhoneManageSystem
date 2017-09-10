package com.wfy.web.dto;

import com.wfy.web.model.Role;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Administrator on 2017/9/9, good luck.
 */
@Component
public class MenuRoleDto {
    List<String> menuIds;
    Role role;

    public List<String> getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(List<String> menuIds) {
        this.menuIds = menuIds;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "RoleDto{" +
                "actionUrls=" + menuIds +
                ", role=" + role +
                '}';
    }
}
