package com.thelittlegym.mobile.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by hibernate on 2017/4/21.
 */
@Entity
@Data
@Table(name="feedback")
public class Feedback {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String franchisee;
    private String contactTel;
    private String details;
    private Date createTime;
    private String type;
    private Boolean handled;
    private Date handledTime;


}
