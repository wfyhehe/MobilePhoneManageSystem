package com.wfy.web.dao;

import com.wfy.web.model.Menu;
import com.wfy.web.model.Role;
import org.hibernate.query.Query;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/7/16.
 */
@Repository
public class MenuDao {
    private HibernateTemplate hibernateTemplate;

    public MenuDao() {
    }

    public HibernateTemplate getHibernateTemplate() {
        return hibernateTemplate;
    }

    @Resource
    public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

    public List<Menu> getMenus(Menu parentMenu, List<Role> roles) {
        List<Menu> list = hibernateTemplate.execute(
                session -> {
                    StringBuffer strRoles = null;
                    StringBuffer sql = new StringBuffer();
                    sql.append("select m.*");
                    sql.append(" from t_menu as m");
                    sql.append(" inner join t_role_menu as r_m");
                    sql.append(" inner join t_role as r");
                    sql.append(" on m.id = r_m.menu_id");
                    sql.append(" and r.id = r_m.role_id");
                    if (parentMenu != null) {
                        sql.append(" where m.id = ?");
                        if (roles != null) {
                            sql.append(" and r.id in (?)");
                            strRoles = new StringBuffer(); // 拼接roleIds
                            for (Role role : roles) {
                                strRoles.append(role.getId());
                                strRoles.append(",");
                            }
                            strRoles.deleteCharAt(strRoles.length() - 1); // 删除逗号
                        }
                    } else if (roles != null) {
                        sql.append(" where r.id in (?)");
                        for (Role role : roles) {
                            strRoles.append(role.getId());
                            strRoles.append(",");
                        }
                        strRoles.deleteCharAt(strRoles.length() - 1); // 删除逗号
                    }
                    Query query = session.createNativeQuery(sql.toString())
                            .addEntity(Menu.class);
                    if (parentMenu != null) {
                        query.setParameter(1, parentMenu.getId());
                    }
                    if (strRoles != null) {
                        query.setParameter(2, strRoles.toString());
                    }
                    query.list();
                    return (List<Menu>) query.list();
                }
        );
        return list;
    }

    public List<Menu> getTopMenus(List<Role> roles) {
        List<Menu> list = hibernateTemplate.execute(
                session -> {
                    StringBuffer strRoles = null;
                    StringBuffer sql = new StringBuffer();
                    sql.append("select m.*");
                    sql.append(" from t_menu as m");
                    sql.append(" inner join t_role_menu as r_m");
                    sql.append(" inner join t_role as r");
                    sql.append(" on m.id = r_m.menu_id");
                    sql.append(" and r.id = r_m.role_id");
                    sql.append(" where m.parent_id is null");
                    if (roles != null) {
                        sql.append(" where r.id in (?)");
                        strRoles = new StringBuffer(); // 拼接roleIds
                        for (Role role : roles) {
                            strRoles.append(role.getId());
                            strRoles.append(",");
                        }
                        strRoles.deleteCharAt(strRoles.length() - 1); // 删除逗号
                    } else if (roles != null) {
                        sql.append(" where r.id in (?)");
                        for (Role role : roles) {
                            strRoles.append(role.getId());
                            strRoles.append(",");
                        }
                        strRoles.deleteCharAt(strRoles.length() - 1); // 删除逗号
                    }
                    Query query = session.createNativeQuery(sql.toString())
                            .addEntity(Menu.class);
                    if (strRoles != null) {
                        query.setParameter(1, strRoles.toString());
                    }
                    query.list();
                    return (List<Menu>) query.list();
                }
        );
        return list;
    }
}
