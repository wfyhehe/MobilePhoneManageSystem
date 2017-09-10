package com.wfy.web.dto;

import com.wfy.web.model.Role;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Administrator on 2017/9/9, good luck.
 */
@Component
public class ActionRoleDto {
    List<String> actionUrls;
    Role role;

    public List<String> getActionUrls() {
        return actionUrls;
    }

    public void setActionUrls(List<String> actionUrls) {
        this.actionUrls = actionUrls;
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
                "actionUrls=" + actionUrls +
                ", role=" + role +
                '}';
    }
}
