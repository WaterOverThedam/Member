package com.thelittlegym.mobile.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by TONY on 2018/1/10.
 */
@Entity
@Data
public class Prize {
    @Id
    private Integer id;
    private String name;
    private String tel;
    private Boolean used;
    private Date createTime=new Date();
}
