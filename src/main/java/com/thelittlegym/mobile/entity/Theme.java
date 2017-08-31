package com.thelittlegym.mobile.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by TONY on 2017/8/26.
 */
@Entity
@Data
@Table(name="theme")
public class Theme {
    @Id
    @GeneratedValue
    private Integer id;
    private Date createTime;
    private String name;
    private Integer weekNum;
    private String detail;
    private Date beginDate;
    private Date endDate;
    private String type;
    private String videoSrc;
    private Boolean isDelete;

}