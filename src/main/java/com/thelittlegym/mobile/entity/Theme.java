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
public class Theme {
    @Id
    @GeneratedValue
    private Integer id;
    private Date createTime;
    private String name;
    private Integer weekNum;
    private Date beginDate;
    private Date endDate;
    private String videoSrc;
    private Boolean isShow;
    private Boolean isDelete;

}