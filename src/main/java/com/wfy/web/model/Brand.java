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
@Table(name = "t_brand")
@JsonIgnoreProperties(ignoreUnknown = true)
@DynamicUpdate
public class Brand {
    private String name;

    public Brand() {
    }

    public Brand(String name) {
        this.name = name;
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
        return "Brand{" +
                "name='" + name + '\'' +
                '}';
    }
}
