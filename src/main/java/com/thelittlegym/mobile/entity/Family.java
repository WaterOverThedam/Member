package com.thelittlegym.mobile.entity;

import lombok.Data;

/**
 * Created by hibernate on 2017/4/10.
 */
//json映射模板
@Data
public class Family {
    private Integer id;
    private String name;
    private String tel;
    private String familyName;
    private String childname;
    private String gym;
    private String gymcode;
    private String idzx;
    private String city;
    private String addr;
    private Integer pointed;

}
