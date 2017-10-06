package com.thelittlegym.mobile.exception;

import com.thelittlegym.mobile.enums.ResultEnum;

/**
 * Created by Tony on 2017/9/13.
 */
public class MyException extends RuntimeException {
    private Integer code;
    public MyException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code=resultEnum.getCode();
    }
    public MyException(String msg) {
        super(msg);
        this.code=-1;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
