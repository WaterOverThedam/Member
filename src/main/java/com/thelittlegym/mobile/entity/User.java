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
    @Column(name="telephone")
    private String tel;
    private String email;
    @Column(name="create_time")
    private Date createTime;
    @Column(name="isdelete")
    private Boolean isDelete;
    private String head_src;
    @Column(name="id_family")
    private Integer idFamily;

}