package com.wfy.web.dao;

import com.wfy.web.model.Menu;
import com.wfy.web.model.MenuType;
import com.wfy.web.model.Role;
import org.hibernate.query.Query;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2017/7/16.
 */
@Repository
public class MenuDao {

    @Resource
    private HibernateTemplate hibernateTemplate;

    public MenuDao() {
    }

    public List<Menu> getMenus(Menu parentMenu, Set<Role> roles) {
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
                    Query query = session.createNativeQuery(sql.toString()).addEntity(Menu.class);
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
        list.sort(Comparator.comparingInt(Menu::getSortOrder));
        for (Menu m : list) {
            if (m.getChildren() != null) {
                m.getChildren().sort(Comparator.comparingInt(Menu::getSortOrder));
            }
        }
        return list;
    }

    public List<Menu> getTopMenus(List<Role> roles) {
        List<Menu> list = hibernateTemplate.execute(
                session -> {
                    StringBuffer strRoles = null;
                    StringBuffer sql = new StringBuffer();
                    sql.append("select distinct m.*");
                    sql.append(" from t_menu as m");
                    sql.append(" inner join t_role_menu as r_m");
                    sql.append(" inner join t_role as r");
                    sql.append(" on m.id = r_m.menu_id");
                    sql.append(" and r.id = r_m.role_id");
                    sql.append(" where m.parent_id is null");
                    if (roles != null) {
                        sql.append(" and r.id in (?)");
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
                    Query query = session.createNativeQuery(sql.toString()).addEntity(Menu.class);
                    if (strRoles != null) {
                        query.setParameter(1, strRoles.toString());
                    }
                    return (List<Menu>) query.list();
                }
        );
        list.sort(Comparator.comparingInt(Menu::getSortOrder));
        for (Menu m : list) {
            if (m.getChildren() != null) {
                m.getChildren().sort(Comparator.comparingInt(Menu::getSortOrder));
            }
        }
        return list;
    }

    public String save(Menu menu) {
        return (String) hibernateTemplate.save(menu);
    }

    public Menu getMenuById(String id) {
        String hql = "from Menu m where m.id = ?";
        List<Menu> list = (List<Menu>) hibernateTemplate.find(hql, id);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    public Menu getMenuByName(String name) {
        String hql = "from Menu m where m.name = ?";
        List<Menu> list = (List<Menu>) hibernateTemplate.find(hql, name);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    public void update(Menu menu) {
        hibernateTemplate.update(menu);
    }

    public Menu getPre(Menu menu) {
        List<Menu> list = null;
        if (menu.getType() == MenuType.PARENT) {
            String hql = "from Menu m where m.sortOrder < ? and m.parent is null";
            list = (List<Menu>) hibernateTemplate.find(hql, menu.getSortOrder());
        } else if (menu.getType() == MenuType.NODE) {
            String hql = "from Menu m where m.sortOrder < ? and m.parent = ?";
            list = (List<Menu>) hibernateTemplate.find(hql, menu.getSortOrder(), menu.getParent());
        }
        if (list != null && list.size() > 0) {
            Menu ret = list.get(0);
            for (Menu m : list) {
                if (m.getSortOrder() > ret.getSortOrder()) {
                    ret = m;
                }
            }
            return ret;
        } else {
            return null;
        }
    }

    public Menu getSuc(Menu menu) {
        List<Menu> list = null;
        if (menu.getType() == MenuType.PARENT) {
            String hql = "from Menu m where m.sortOrder > ? and m.parent is null";
            list = (List<Menu>) hibernateTemplate.find(hql, menu.getSortOrder());
        } else if (menu.getType() == MenuType.NODE) {
            String hql = "from Menu m where m.sortOrder > ? and m.parent = ?";
            list = (List<Menu>) hibernateTemplate.find(hql, menu.getSortOrder(), menu.getParent());
        }
        if (list != null && list.size() > 0) {
            Menu ret = list.get(0);
            for (Menu m : list) {
                if (m.getSortOrder() < ret.getSortOrder()) {
                    ret = m;
                }
            }
            return ret;
        } else {
            return null;
        }
    }

    public void delete(Menu menu) {
        hibernateTemplate.clear();
        hibernateTemplate.delete(menu);
    }
}
