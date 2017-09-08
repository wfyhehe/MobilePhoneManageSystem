package com.wfy.web.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.wfy.web.model.enums.CheckStatus;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 2017/9/2.
 */
@Entity
@Table(name = "t_mobile_stock")
@JsonIgnoreProperties(ignoreUnknown = true, value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@DynamicUpdate
public class MobileStock {
    /* id                VARCHAR(32) PRIMARY KEY,
     model_id          VARCHAR(15) NOT NULL,
     color             VARCHAR(64) NOT NULL,
     config            VARCHAR(64),
     first_supplier_id VARCHAR(15),
     first_in_time     DATETIME,
     last_supplier_id  VARCHAR(15),
     last_in_time      DATETIME,
     buy_price         DECIMAL(20, 2),
     cost              DECIMAL(20, 2), # 由于返利和保价存在，成本可能比入库价低
     loss_amount       DECIMAL(20, 2), # 次品的损失金额
     dept_id           CHAR(32),
     status            TINYINT, # 0: 未审核 1: 已审核 2: 删除*/
    private String id;
    private MobileModel mobileModel;
    private Color color;
    private Config config;
    private Supplier firstSupplier;
    private Date firstInTime;
    private Supplier lastSupplier;
    private Date lastInTime;
    private MobileInbound mobileInbound;
    private double buyPrice;
    private double cost;
    private double lossAmount;
    private Dept dept;
    private CheckStatus status;

    @Override
    public String toString() {
        return "MobileStock{" +
                "id='" + id + '\'' +
                ", mobileModel=" + mobileModel +
                ", color=" + color +
                ", config=" + config +
                ", firstSupplier=" + firstSupplier +
                ", firstInTime=" + firstInTime +
                ", lastSupplier=" + lastSupplier +
                ", lastInTime=" + lastInTime +
                ", mobileInbound(id)=" + ((mobileInbound != null) ? mobileInbound.getId() : "") +
                ", buyPrice=" + buyPrice +
                ", cost=" + cost +
                ", lossAmount=" + lossAmount +
                ", dept=" + dept +
                ", status=" + status +
                '}';
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "inbound_id")
    public MobileInbound getMobileInbound() {
        return mobileInbound;
    }

    public void setMobileInbound(MobileInbound mobileInbound) {
        this.mobileInbound = mobileInbound;
    }

    @Id
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @ManyToOne
    @JoinColumn(name = "first_supplier_id")
    public Supplier getFirstSupplier() {
        return firstSupplier;
    }

    public void setFirstSupplier(Supplier firstSupplier) {
        this.firstSupplier = firstSupplier;
    }

    @Column(name = "first_in_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getFirstInTime() {
        return firstInTime;
    }

    public void setFirstInTime(Date firstInTime) {
        this.firstInTime = firstInTime;
    }

    @ManyToOne
    @JoinColumn(name = "last_supplier_id")
    public Supplier getLastSupplier() {
        return lastSupplier;
    }

    public void setLastSupplier(Supplier lastSupplier) {
        this.lastSupplier = lastSupplier;
    }

    @Column(name = "last_in_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getLastInTime() {
        return lastInTime;
    }

    public void setLastInTime(Date lastInTime) {
        this.lastInTime = lastInTime;
    }

    @Column(name = "buy_price")
    public double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }

    @Column(name = "cost")
    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Column(name = "loss_amount")
    public double getLossAmount() {
        return lossAmount;
    }

    public void setLossAmount(double lossAmount) {
        this.lossAmount = lossAmount;
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
    public CheckStatus getStatus() {
        return status;
    }

    public void setStatus(CheckStatus status) {
        this.status = status;
    }

}
