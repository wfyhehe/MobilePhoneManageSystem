package com.wfy.web.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wfy.web.model.enums.CheckStatus;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 2017/9/2.
 */
@Entity
@Table(name = "t_mobile_inbound")
@JsonIgnoreProperties(ignoreUnknown = true)
@DynamicUpdate
public class MobileInbound {
    /*    id            VARCHAR(32) PRIMARY KEY,
        supplier_id   VARCHAR(15) NOT NULL,
        model_id      VARCHAR(15) NOT NULL,
        color         VARCHAR(64),
        config        VARCHAR(64),
        buy_price     DECIMAL(20, 2),
        quantity      INT,
        amount        DECIMAL(20, 2), # 冗余字段，增加效率
        input_time    DATETIME,
        input_user_id CHAR(32),
        check_time    DATETIME,
        check_user_id CHAR(32),
        check_status  TINYINT, # 0: 未审核 1: 已审核 2: 删除
        remark        TEXT,
        dept_id       CHAR(32),*/
    private String id;
    private Supplier supplier;
    private MobileModel mobileModel;
    private Color color;
    private Config config;
    private double buyPrice;
    private int quantity;
    private double amount;
    private Date inputTime;
    private User inputUser;
    private Date checkTime;
    private User checkUser;
    private CheckStatus status;
    private Dept dept;
    private String remark;

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

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    @ManyToOne
    @JoinColumn(name = "model_id")
    public MobileModel getMobileModel() {
        return mobileModel;
    }

    public void setMobileModel(MobileModel mobileModel) {
        this.mobileModel = mobileModel;
    }

    @ManyToOne
    @JoinColumn(name = "color")
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @ManyToOne
    @JoinColumn(name = "config")
    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    @Column(name = "buy_price")
    public double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
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

    @Column(name = "input_user_id")
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

    @Column(name = "check_user_id")
    public User getCheckUser() {
        return checkUser;
    }

    public void setCheckUser(User checkUser) {
        this.checkUser = checkUser;
    }

    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    public CheckStatus getStatus() {
        return status;
    }

    public void setStatus(CheckStatus status) {
        this.status = status;
    }

    @ManyToOne
    @JoinColumn(name = "dept_id")
    public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
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
        return "MobileInbound{" +
                "id='" + id + '\'' +
                ", supplier=" + supplier +
                ", mobileModel=" + mobileModel +
                ", color=" + color +
                ", config=" + config +
                ", buyPrice=" + buyPrice +
                ", quantity=" + quantity +
                ", amount=" + amount +
                ", inputTime=" + inputTime +
                ", inputUser=" + inputUser +
                ", checkTime=" + checkTime +
                ", checkUser=" + checkUser +
                ", status=" + status +
                ", dept=" + dept +
                ", remark='" + remark + '\'' +
                '}';
    }
}
