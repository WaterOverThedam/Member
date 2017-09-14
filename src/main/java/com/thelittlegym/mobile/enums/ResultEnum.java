package com.thelittlegym.mobile.enums;

import lombok.Getter;


@Getter
public enum ResultEnum {
    SUCCESS(0,"操作成功"),
    FAILURE(-101,"操作失败"),
    DEL_SUCCESS(0, "删除成功"),
    DEL_FAIL(-201, "删除失败"),
    LOGIN_SUCCESS(0, "登陆成功"),
    LOGIN_WRONG_PWD(-301, "密码错误"),
    LOGIN_EXCEPTION(-302, "登陆异常"),
    LOGIN_USER_NO_EXIST(-303,"该用户不存在"),
    REGISTER_SUCCESS(0,"注册成功"),
    REGISTER_USER_EXIST(-401,"该用户名已存在，请返回直接登录"),
    REGISTER_EXCEPTION(-402,"异常：注册失败"),
    REGISTER_ALLOW(0,"'该号码是会员帐号,可以注册√'"),
    REGISTER_NOT_ALLOW(-403,"该号码非会员"),
    COUPON_SAVE_EXIST(-11,"该用户名已存在.."),
    ENROL_SUCCESS(0,"报名成功"),
    ENROL_EXIST(-21,"该用户已报名"),
    ENROL_CHECKSUM_ERR(-31,"报名失败:验证码错误或者失效"),
    ENROL_CHECKSUM_OVERDUE(-41,"报名失败:验证码过期"),
    FEEDBACK_SUCCESS(0,"反馈成功"),
    FEEDBACK_FAILURE(-51,"反馈失败"),
    UPLOAD_SUCCESS(-304,"  上传成功"),
    UPLOAD_TRY_LATER(-304,"  请重新登录后再试"),
    IS_REGISTERED(0,"已注册"),
    IS_NOT_REGISTERED(0,"未注册用户"),
    CHECKSUM_SUCCESS(0,"验证通过"),
    CHECKSUM_WRONG(0,"验证码错误"),
    CHECKSUM_OVERDUE(0,"验证码已过期"),
    VALIDATE_TEL(-61,"手机号必填"),
    VALIDATE_NAME(-62,"姓名必填"),
    VALIDATE_TYPE(-62,"类型必填"),
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
