package com.thelittlegym.mobile.entity;


import lombok.Data;

import javax.persistence.*;
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roleId", nullable = true)
    private Role role;
    //默认值
    private Date createTime = new Date();
    private boolean isDelete;

}
