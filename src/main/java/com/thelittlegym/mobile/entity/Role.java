package com.thelittlegym.mobile.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by TONY on 2017/9/17.
 */
@Data
@Entity
public class Role {
    @Id
    @GeneratedValue
    private Integer id;
    private String  name;
    private String  acl;
}
