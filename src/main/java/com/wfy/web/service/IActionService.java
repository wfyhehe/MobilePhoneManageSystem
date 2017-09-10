package com.wfy.web.service;

import com.wfy.web.dto.ActionRoleDto;
import com.wfy.web.model.Action;

import java.util.List;

/**
 * Created by Administrator on 2017/9/7, good luck.
 */
public interface IActionService {
    List<Action> getActions();

    void addOrUpdateAction(Action action);

    void updateFromRole(ActionRoleDto actionRoleDto);

    void update(Action action);

    Action getActionByUrl(String url);

    Action getActionByName(String name);

    boolean urlExists(String url);
}
