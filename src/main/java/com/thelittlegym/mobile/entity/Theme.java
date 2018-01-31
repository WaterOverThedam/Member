package com.thelittlegym.mobile.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date beginDate;
    private String course;
    private String name;
    private Integer weekNum;
    private String videoSrc;
    private Boolean isShow;
    private String search;

    @Override
    public String toString() {
        return "Theme{" +
                "name='" + name + '\'' +
                ", url=" + videoSrc+
                '}';
    }
}