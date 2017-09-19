package com.wfy.web.dao;

import com.wfy.web.model.Action;
import com.wfy.web.model.User;
import org.hibernate.query.NativeQuery;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/7/16.
 */
@Repository
public class ActionDao {
    @Resource
    private HibernateTemplate hibernateTemplate;

    private List<Action> normalizeActions(List<Action> actions) {
        for (Action action : actions) {
            normalizeAction(action);
        }
        return actions;
    }

    private Action normalizeAction(Action action) {
        return action;
    }

    private Action extractAndNormalizeFirstAction(List<Action> list) {
        if (list.size() > 0) {
            Action action = list.get(0);
            return normalizeAction(action);
        } else {
            return null;
        }
    }

    public List<Action> getAll() {
        String hql = "from Action a order by a.url";
        List<Action> actions = (List<Action>) hibernateTemplate.find(hql);
        return normalizeActions(actions);
    }

    public Action getActionByName(String name) {
        String hql = "from Action a where a.name = ?";
        List<Action> list = (List<Action>) hibernateTemplate.find(hql, name);
        return extractAndNormalizeFirstAction(list);
    }

    public Action getActionByUrl(String url) {
        String hql = "from Action a where a.url = ?";
        List<Action> list = (List<Action>) hibernateTemplate.find(hql, url);
        return extractAndNormalizeFirstAction(list);
    }

    public void update(Action action) {
        hibernateTemplate.update(action);
    }

    public void saveOrUpdate(Action action) {
        hibernateTemplate.merge(action);
    }

    public boolean urlExists(String url) {
        String hql = "select count(*) from Action a where a.url = ?";
        List<Long> list = (List<Long>) hibernateTemplate.find(hql, url);
        return list.get(0) > 0;
    }

    public void merge(Action action) {
        hibernateTemplate.merge(action);
    }

    public List<Action> getActions() {
        String hql = "from Action a";
        List<Action> actions = (List<Action>) hibernateTemplate.find(hql);
        return normalizeActions(actions);
    }

    public List<String> getActionsByUser(User user) {
        String sql = "select distinct t_action.url " +
                "from t_action " +
                "inner join t_role_action " +
                "inner join t_role " +
                "inner join t_role_user " +
                "inner join t_user " +
                "on t_action.url = t_role_action.action_url " +
                "and t_role.id = t_role_action.role_id " +
                "and t_role.id = t_role_user.role_id " +
                "and t_user.id = t_role_user.user_id " +
                "where t_user.id = :id " +
                "or t_action.type = 0";
        List<String> actionUrls = hibernateTemplate.executeWithNativeSession(session -> {
            NativeQuery query = session.createNativeQuery(sql);
            query.setParameter("id", user.getId());
            return query.list();
        });
        // ActionType = 0 的为普通动作，无需授权即可访问
        return actionUrls;
    }

    public List<String> getTouristActions() {
        String sql = "select distinct t_action.url " +
                "from t_action " +
                "inner join t_role_action " +
                "inner join t_role " +
                "on t_action.url = t_role_action.action_url " +
                "and t_role.id = t_role_action.role_id " +
                "where t_role.name = :name " +
                "or t_action.type = 0";
        List<String> actionUrls = hibernateTemplate.executeWithNativeSession(session -> {
            NativeQuery query = session.createNativeQuery(sql);
            query.setParameter("name", "游客");
            return query.list();
        });
        // ActionType = 0 的为普通动作，无需授权即可访问
        return actionUrls;
    }
}
