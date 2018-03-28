package com.thelittlegym.mobile.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.thelittlegym.mobile.utils.serializer.Date2LongSerializer;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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
    //@JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;
    @Column(name="isdelete")
    private Boolean isDelete;
    private String head_src;
    //family信息部分
    @Column(name="id_family")
    private Integer idFamily;
    private String familyName;
    private String familyTitle;
    @Column(name="telephone")
    private String tel;
    private String childName;
    private String gym;
    private String gymcode;
    private String idzx;
    private String city;
    private String addr;
    private String openId ="";

}