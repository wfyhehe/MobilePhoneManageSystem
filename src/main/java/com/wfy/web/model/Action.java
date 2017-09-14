package com.wfy.web.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wfy.web.model.enums.ActionType;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/7/15.
 */

@Entity
@Table(name = "t_action")
@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "url")
@DynamicUpdate
public class Action implements Serializable {
    private String name;
    private String remark;
    private ActionType type;
    private String url;
    private List<Role> roles;

    public Action() {
    }

    public Action(String name, String url, ActionType type) {
        this.name = name;
        this.type = type;
        this.url = url;
    }

    @Id
    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "t_role_action",
            joinColumns = @JoinColumn(name = "action_url", referencedColumnName = "url"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }


    @Override
    public String toString() {
        return "Action{" +
                "name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", type=" + type +
                ", url='" + url + '\'' +
//                ", roles=" + roles +
                '}';
    }
}
