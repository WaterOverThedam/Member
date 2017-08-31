package com.thelittlegym.mobile.entity;

import lombok.Data;

import java.util.Date;

/**
 * Created by 汁 on 2017/4/9.
 */
@Data
public class GymClass {
    private String idgym;
    private Date date;
    private String time;
    private String course;
    private String kq;

}
