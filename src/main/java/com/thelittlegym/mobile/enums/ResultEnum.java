package com.thelittlegym.mobile.enums;

import lombok.Getter;

/**
 * Created by 廖师兄
 * 2017-06-11 18:56
 */
@Getter
public enum ResultEnum {

    DEL_SUCCESS(true, "删除成功"),
    DEL_FAIL(false, "删除失败"),
    LOGIN_SUCCESS(true, "登录成功"),
    LOGIN_WRONG_PWD(false, "密码错误"),
    LOGIN_USER_NO_EXIST(false,"该用户不存在!"),
    REGISTER_SUCCESS(true,"注册成功"),
    REGISTER_USER_EXIST(false,"该用户名已存在.."),
    COUPON_SAVE_EXIST(false,"该用户名已存在.."),
    ENROL_EXIST(false,"该用户已报名"),
    ENROL_CHECKSUM_ERR(false,"报名失败:验证码错误或者失效"),
    ENROL_CHECKSUM_OVERDUE(false,"报名失败:验证码过期"),
    ENROL_SUCCESS(true,"报名成功"),
    SUCCESS(true,"操作成功"),
    FAILURE(false,"操作失败"),
    ;


    private Boolean code;
    private String message;

    ResultEnum(Boolean status, String message) {
        this.code = code;
        this.message = message;
    }
}
