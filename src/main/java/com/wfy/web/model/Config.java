package com.wfy.web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Administrator on 2017/9/2.
 */
@Entity
@Table(name = "t_config")
@JsonIgnoreProperties(ignoreUnknown = true)
@DynamicUpdate
public class Config {
    private String name;

    public Config(String name) {
        this.name = name;
    }

    public Config() {
    }

    @Id
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Config{" +
                "name='" + name + '\'' +
                '}';
    }
}
