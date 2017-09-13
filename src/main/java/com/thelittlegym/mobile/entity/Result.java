package com.thelittlegym.mobile.entity;

import lombok.Data;

/**
 * Created by Tony on 2017/9/13.
 */
@Data
public class Result <T> {
    private Integer code;
    private String  msg;
    private T data;

}
