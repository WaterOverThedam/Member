package com.thelittlegym.mobile.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by hibernate on 2017/6/21.
 */
@Entity
@Data
@Table(name="points")
public class Points {
    @Id
    @GeneratedValue
    private Integer id;
    //活动积分来源类型
    private String type;
    //积分数量
    private Integer num;
    private Date createTime;
    private String tel;
    private String zx;

}
