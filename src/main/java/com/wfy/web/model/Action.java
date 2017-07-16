package com.wfy.web.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/7/15.
 */

@Entity
@Table(name = "t_action")
public class Action implements Serializable {
    private int id;
    private String name;
    private String remark;
    private ActionType type;
    private List<Role> roles;

    public Action() {
    }

    public Action(String name, ActionType type) {
        this.name = name;
        this.type = type;
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
    public ActionType getType() {
        return type;
    }

    public void setType(ActionType type) {
        this.type = type;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "t_role_action",
            joinColumns = @JoinColumn(name = "action_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }


    @Override
    public String toString() {
        return "Action{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", type=" + type +
                ", roles=" + roles +
                '}';
    }

}
