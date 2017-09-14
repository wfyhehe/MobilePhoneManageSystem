package com.wfy.web.service.impl;

import com.wfy.web.dao.ActionDao;
import com.wfy.web.dto.ActionRoleDto;
import com.wfy.web.model.Action;
import com.wfy.web.model.Role;
import com.wfy.web.model.User;
import com.wfy.web.service.IActionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/8/26.
 */
@Service("iActionService")
@Transactional
public class ActionServiceImpl implements IActionService {

    @Resource
    private ActionDao actionDao;

    @Override
    public List<Action> getActions() {
        return actionDao.getAll();
    }

    @Override
    public void addOrUpdateAction(Action action) {
        actionDao.saveOrUpdate(action);
    }

    @Override
    public void update(Action action) {
        Action oldAction = actionDao.getActionByUrl(action.getUrl());
        if (action.getName() == null) {
            action.setName(oldAction.getName());
        }
        if (action.getRemark() == null) {
            action.setRemark(oldAction.getRemark());
        }
        if (action.getRoles() == null) {
            action.setRoles(oldAction.getRoles());
        }
        if (action.getType() == null) {
            action.setType(oldAction.getType());
        }
        actionDao.update(action);
    }

    @Override
    public void updateFromRole(ActionRoleDto actionRoleDto) {
        Role role = actionRoleDto.getRole();
        List<Action> actions = actionDao.getActions();
        for (Action action : actions) {
            System.out.println(action);
            if (actionRoleDto.getActionUrls().contains(action.getUrl())) {
                if (!action.getRoles().contains(role)) {
                    action.getRoles().add(role);
                }
            } else {
                action.getRoles().remove(role);
            }
            System.out.println(action);
            actionDao.merge(action);
        }
    }

    @Override
    public Action getActionByUrl(String url) {
        return actionDao.getActionByUrl(url);
    }

    @Override
    public Action getActionByName(String name) {
        return actionDao.getActionByName(name);
    }

    @Override
    public List<String> getActionsByUser(User user) {
        return actionDao.getActionsByUser(user);
    }

    @Override
    public boolean urlExists(String url) {
        return actionDao.urlExists(url);
    }
}
