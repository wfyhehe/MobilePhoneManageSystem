package com.wfy.web.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wfy.web.model.enums.BusinessType;
import com.wfy.web.model.enums.CheckStatus;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 2017/9/2.
 */

@Entity
@Table(name = "t_supplier_trade_detail")
@JsonIgnoreProperties(ignoreUnknown = true)
@DynamicUpdate
public class SupplierTradeDetail {
    /* id            VARCHAR(32) PRIMARY KEY,
     supplier_id   VARCHAR(15) NOT NULL,
     business_type TINYINT,
     model_id      VARCHAR(15) NOT NULL,
     price         DECIMAL(20, 2),
     quantity      INT,
     amount        DECIMAL(20, 2), # 冗余字段，增加效率
     input_time    DATETIME,
     input_user_id CHAR(32),
     check_time    DATETIME,
     check_user_id CHAR(32),
     remark        TEXT,
     dept_id       CHAR(32),
     status        TINYINT, # 0: 未审核 1: 已审核 2: 删除*/
    private String id;
    private BusinessType businessType;
    private MobileModel mobileModel;
    private double price;
    private int quantity;
    private double amount;
    private Date inputTime;
    private User inputUser;
    private Date checkTime;
    private User checkUser;
    private String remark;
    private Dept dept;
    private CheckStatus checkStatus;

    @Id
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "business_type")
    @Enumerated(EnumType.ORDINAL)
    public BusinessType getBusinessType() {
        return businessType;
    }

    public void setBusinessType(BusinessType businessType) {
        this.businessType = businessType;
    }

    @ManyToOne
    @JoinColumn(name = "model_id")
    public MobileModel getMobileModel() {
        return mobileModel;
    }

    public void setMobileModel(MobileModel mobileModel) {
        this.mobileModel = mobileModel;
    }

    @Column(name = "price")
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Column(name = "quantity")
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Column(name = "amount")
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Column(name = "input_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getInputTime() {
        return inputTime;
    }

    public void setInputTime(Date inputTime) {
        this.inputTime = inputTime;
    }

    @ManyToOne
    @JoinColumn(name = "input_user_id")
    public User getInputUser() {
        return inputUser;
    }

    public void setInputUser(User inputUser) {
        this.inputUser = inputUser;
    }

    @Column(name = "check_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    @ManyToOne
    @JoinColumn(name = "check_user_id")
    public User getCheckUser() {
        return checkUser;
    }

    public void setCheckUser(User checkUser) {
        this.checkUser = checkUser;
    }

    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @ManyToOne
    @JoinColumn(name = "dept_id")
    public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    public CheckStatus getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(CheckStatus checkStatus) {
        this.checkStatus = checkStatus;
    }

    @Override
    public String toString() {
        return "SupplierTradeDetail{" +
                "id='" + id + '\'' +
                ", businessType=" + businessType +
                ", mobileModel=" + mobileModel +
                ", price=" + price +
                ", quantity=" + quantity +
                ", amount=" + amount +
                ", inputTime=" + inputTime +
                ", inputUser=" + inputUser +
                ", checkTime=" + checkTime +
                ", checkUser=" + checkUser +
                ", remark='" + remark + '\'' +
                ", dept=" + dept +
                ", checkStatus=" + checkStatus +
                '}';
    }
}
