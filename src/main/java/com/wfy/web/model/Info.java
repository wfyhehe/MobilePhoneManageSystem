package com.wfy.web.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by Administrator on 2017/9/15, good luck.
 */
@Entity
@Table(name = "t_info")
@JsonIgnoreProperties(ignoreUnknown = true)
@DynamicUpdate
public class Info {
    /*id       CHAR(32) NOT NULL,
      content  TEXT,
      position VARCHAR(32),*/
    private String id;
    private String content;
    private String position;

    public Info(String id) {
        this.id = id;
    }

    public Info() {
    }

    public Info(String content, String position) {
        this.content = content;
        this.position = position;
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

    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(name = "position")
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
