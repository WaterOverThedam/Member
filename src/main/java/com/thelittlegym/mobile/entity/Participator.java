package com.thelittlegym.mobile.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by hibernate on 2017/5/25.
 */
@Entity
@Data
@Table(name="participator")
public class Participator {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String phone;
    private String actid;
    private Date createTime;


}
