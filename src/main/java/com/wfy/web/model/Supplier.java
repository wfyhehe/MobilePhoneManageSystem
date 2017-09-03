package com.wfy.web.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * Created by Administrator on 2017/9/1.
 */
@Entity
@Table(name = "t_supplier")
@JsonIgnoreProperties(ignoreUnknown = true)
@DynamicUpdate
public class Supplier {
    private String id;
    private String name;
    private SupplierType type;
    private String contact;
    private String tel;
    private String email;
    private String address;
    private String remark;
    private boolean deleted;

    @Column(name = "deleted")
    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Id
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

    @ManyToOne
    @JoinColumn(name = "type")
    public SupplierType getType() {
        return type;
    }

    public void setType(SupplierType type) {
        this.type = type;
    }

    @Column(name = "contact")
    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Column(name = "tel")
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
        return "Supplier{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", contact='" + contact + '\'' +
                ", tel='" + tel + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", remark='" + remark + '\'' +
                ", deleted=" + deleted +
                '}';
    }
}
