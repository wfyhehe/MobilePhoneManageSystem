package com.wfy.web.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by Administrator on 2017/9/1.
 */
@Entity
@Table(name = "t_rebate_price")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@DynamicUpdate
public class RebatePrice {

    private String id;
    private RebateType rebateType;
    private MobileModel mobileModel;
    private double price;

    @ManyToOne
    @JoinColumn(name = "rebate_type_id")
    public RebateType getRebateType() {
        return rebateType;
    }

    public void setRebateType(RebateType rebateType) {
        this.rebateType = rebateType;
    }

    @ManyToOne
    @JoinColumn(name = "mobile_model_id")
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


    @Override
    public String toString() {
        return "RebatePrice{" +
                "id='" + id + '\'' +
                ", rebateType=" + rebateType +
                ", mobileModel=" + mobileModel +
                ", price=" + price +
                '}';
    }
}
