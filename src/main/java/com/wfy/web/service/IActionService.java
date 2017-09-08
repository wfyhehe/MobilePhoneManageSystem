package com.wfy.web.service;

import com.wfy.web.model.Action;

import java.util.List;

/**
 * Created by Administrator on 2017/9/7, good luck.
 */
public interface IActionService {
    List<Action> getActions();

    void addOrUpdateAction(Action action);

    void updateAction(Action action);

    Action getActionByUrl(String url);

    Action getActionByName(String name);

    boolean urlExists(String url);
}
