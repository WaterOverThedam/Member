package com.thelittlegym.mobile.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name="user")
public class User {
    @Id
    @GeneratedValue
    private Integer id;
    private String username;
    private String password;
    private String email;
    @Column(name="create_time")
    private Date createTime;
    @Column(name="isdelete")
    private Boolean isDelete;
    private String head_src;
    //family信息部分
    @Column(name="id_family")
    private Integer idFamily;
    private String familyName;
    @Column(name="telephone")
    private String tel;
    private String childname;
    private String gym;
    private String idzx;
    private String city;
    private String addr;

}