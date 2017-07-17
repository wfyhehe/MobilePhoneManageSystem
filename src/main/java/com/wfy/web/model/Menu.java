package com.wfy.web.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/7/15.
 */

@Entity
@Table(name = "t_menu")
public class Menu implements Serializable {
    private int id;
    private String name;
    private String remark;
    private MenuType type;
    private Integer sortOrder;
    private Menu parent;
    private List<Menu> children;
    private Action action;
    private List<Role> roles;

    public Menu() {
    }

    public Menu(String name, Integer sortOrder) {
        this.name = name;
        type = MenuType.PARENT;
        this.sortOrder = sortOrder;
    }

    public Menu(String name, MenuType type, Integer sortOrder) {
        this.name = name;
        this.type = type;
        this.sortOrder = sortOrder;
    }

    public Menu(String name, Menu parent, Integer sortOrder) {
        this.name = name;
        this.parent = parent;
        type = MenuType.NODE;
        this.sortOrder = sortOrder;
    }


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER,
            mappedBy = "parent")
    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }


    @ManyToOne
    @JoinColumn(name = "parent_id")
    @JsonIgnore
    public Menu getParent() {
        return parent;
    }

    public void setParent(Menu parent) {
        this.parent = parent;
    }

    @OneToOne
    @JoinColumn(name = "action_id")
    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "type")
    @Enumerated(EnumType.ORDINAL)
    public MenuType getType() {
        return type;
    }

    public void setType(MenuType type) {
        this.type = type;
    }

    @Column(name = "sort_order")
    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }


    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "t_role_menu",
            joinColumns = @JoinColumn(name = "menu_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @JsonIgnore
    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }


    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", type=" + type +
                ", sortOrder=" + sortOrder +
                ", parent=" + parent +
                //", children=" + children + //若既有children又有parent会无限递归爆栈
                ", action=" + action +
                ", roles=" + roles +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Menu menu = (Menu) o;

        return id == menu.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
