package com.thelittlegym.mobile.entity;

import com.thelittlegym.mobile.enums.ResultEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tony on 2017/9/10.
 */
public class Msg {

    private int id;

    private StatusCodeEnum status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public StatusCodeEnum getStatus() {
        return status;
    }

    public void setStatus(StatusCodeEnum status) {
        this.status = status;
    }


    public static enum StatusCodeEnum {
        OK(200,"shit" ), SERVER_ERROR(500, "fuck");

        private static final Map<Integer, StatusCodeEnum> CODE_MAP = new HashMap<Integer, StatusCodeEnum>();

        static {
            for (StatusCodeEnum typeEnum : StatusCodeEnum.values()) {
                CODE_MAP.put(typeEnum.getCode(), typeEnum);
            }
        }

        StatusCodeEnum(int code, String meaning) {
            this.code = code;
            this.meaning = meaning;
        }

        public int getCode() {
            return code;
        }

        public String getMeaning() {
            return meaning;
        }

        public static StatusCodeEnum getEnum(Integer code) {
            return CODE_MAP.get(code);
        }

        private final int code;
        private final String meaning;
    }

}