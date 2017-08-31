package com.thelittlegym.mobile.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by hibernate on 2017/5/19.
 */
@Entity
@Data
@Table(name="admin")
public class Admin {
    @Id
    @GeneratedValue
    private Integer id;
    private String username;
    private String password;
    private String name;
    private Date createTime;
    private boolean isDelete;

}
