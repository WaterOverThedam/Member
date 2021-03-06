package com.thelittlegym.mobile.entity;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Created by hibernate on 2017/5/8.
 */
@Data
public class Child {
    private Integer idhz;
    private String name;
    private String age;
    private String gym;
    private String gender;
    private String rest;
    private String dtend;
    private RankCombo ranking;
    private List<GymClass> gymClasses;
}
