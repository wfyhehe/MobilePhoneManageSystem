package com.wfy.web.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wfy.web.model.enums.MenuType;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2017/7/15.
 */

@Entity
@Table(name = "t_menu")
@JsonIgnoreProperties(ignoreUnknown = true)
@DynamicUpdate
public class Menu implements Serializable {
    private String id;
    private String name;
    private String remark;
    private MenuType type;
    private String path;
    private Integer sortOrder;
    private Menu parent;
    private List<Menu> children;
    private Set<Action> actions;
    private Set<Role> roles;

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

    public Menu(String name, Menu parent, String path, Integer sortOrder) {
        this.name = name;
        this.parent = parent;
        this.path = path;
        type = MenuType.NODE;
        this.sortOrder = sortOrder;
    }

    @Column(name = "path")
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER,mappedBy = "menu")
    public Set<Action> getActions() {
        return actions;
    }

    public void setActions(Set<Action> actions) {
        this.actions = actions;
    }

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
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


    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "t_role_menu",
            joinColumns = @JoinColumn(name = "menu_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
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
                ", actions=" + actions +
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
        return id.hashCode();
    }
}
