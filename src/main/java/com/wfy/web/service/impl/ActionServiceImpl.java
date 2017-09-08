package com.wfy.web.service.impl;

import com.wfy.web.dao.ActionDao;
import com.wfy.web.model.Action;
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
    public void updateAction(Action action) {
        actionDao.update(action);
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
    public boolean urlExists(String url) {
        return actionDao.urlExists(url);
    }
}
