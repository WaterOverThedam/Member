package com.thelittlegym.mobile.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by hibernate on 2017/5/17.
 */
@Entity
@Data
@Table(name="activity")
public class Activity {
    @Id
    @GeneratedValue
    private Integer id;
    private Date createTime;
    private String name;
    private String detail;
    private Date beginDate;
    private Date endDate;
    private String type;
    //收费总类
    private String chargeType;
    //人群
    private String crowd;
    //强度
    private String strength;
    private Boolean isDelete;
    private String bannerSrc;
    private String gyms;
    private String city;

}
