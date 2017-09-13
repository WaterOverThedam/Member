package com.thelittlegym.mobile.enums;

import lombok.Getter;


@Getter
public enum ResultEnum {

    DEL_SUCCESS(1, "删除成功"),
    DEL_FAIL(0, "删除失败"),
    LOGIN_SUCCESS(1, "登陆成功"),
    LOGIN_WRONG_PWD(0, "密码错误"),
    LOGIN_EXCEPTION(0, "登陆异常"),
    LOGIN_USER_NO_EXIST(0,"该用户不存在"),
    REGISTER_SUCCESS(1,"注册成功"),
    REGISTER_USER_EXIST(0,"该用户名已存在"),
    REGISTER_EXCEPTION(0,"异常：注册失败"),
    COUPON_SAVE_EXIST(0,"该用户名已存在.."),
    ENROL_EXIST(0,"该用户已报名"),
    ENROL_CHECKSUM_ERR(0,"报名失败:验证码错误或者失效"),
    ENROL_CHECKSUM_OVERDUE(0,"报名失败:验证码过期"),
    ENROL_SUCCESS(1,"报名成功"),
    SUCCESS(1,"操作成功"),
    FAILURE(0,"操作失败"),
    FEEDBACK_SUCCESS(1,"反馈成功"),
    FEEDBACK_FAILURE(0,"反馈失败"),
    ;

    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public String toJson(){
        return "{" +
                "|code|:" + code +
                ", |message|:|" + message + "|"+
                '}';
   }
    @Override
    public String toString() {
        return "ResultEnum{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
