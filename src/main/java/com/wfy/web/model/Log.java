package com.wfy.web.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wfy.web.model.enums.LogStatus;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/7/14.
 */
@Entity
@Table(name = "t_log")
@JsonIgnoreProperties(ignoreUnknown = true)
@DynamicUpdate
public class Log implements Serializable {
 /* id          INT         NOT NULL PRIMARY KEY,
    ip          VARCHAR(32) NULL,
    create_date DATETIME    NULL,
    status      TINYINT     NULL,
    action_url  VARCHAR(64) NULL,
    user_id     CHAR(32)    NULL,
    remark      TEXT        NULL,*/

    private String id;
    private String ip;
    private Date createDate;
    private LogStatus status;
    private Action action;
    private User user;
    private String remark;

    public Log() {
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

    @Column(name = "ip")
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Column(name = "create_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    public LogStatus getStatus() {
        return status;
    }

    public void setStatus(LogStatus status) {
        this.status = status;
    }

    @ManyToOne
    @JoinColumn(name = "action_url")
    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    @Override
    public String toString() {
        return "Log{" +
                "id='" + id + '\'' +
                ", ip='" + ip + '\'' +
                ", createDate=" + createDate +
                ", status=" + status +
                ", action=" + action +
                ", user=" + user +
                ", remark='" + remark + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Log log = (Log) o;

        return id.equals(log.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
