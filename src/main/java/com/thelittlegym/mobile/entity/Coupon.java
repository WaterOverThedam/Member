package com.thelittlegym.mobile.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by hibernate on 2017/6/16.
 */
@Entity
@Data
@Table(name="Coupon")
public class Coupon {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String type;
    private Float money;
    private Date create_time;
    private Boolean used;
    private String tel;

}
