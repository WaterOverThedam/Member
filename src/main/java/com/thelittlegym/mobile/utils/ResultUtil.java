package com.thelittlegym.mobile.utils;

import com.thelittlegym.mobile.entity.Result;
import com.thelittlegym.mobile.enums.ResultEnum;

/**
 * Created by Tony on 2017/9/13.
 */
public class ResultUtil{
    //成功
    public static Result success(Integer code,String msg,Object object){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(object);
        return  result;
    }

    public static Result success(String msg,Object object){
        return success(0,msg,object);
    }
    public static Result success(ResultEnum resultEnum,Object object){
        return success(resultEnum.getCode(),resultEnum.getMessage(),object);
    }

    public static Result success(String msg){
        return success(0,msg,null);
    }
    public static Result success(ResultEnum resultEnum){
        return success(resultEnum.getCode(),resultEnum.getMessage(),null);
    }
    public static Result success(Object object){
        return success(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMessage(),object);
    }
    public static Result success(){
        return success(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMessage(),null);
    }



    //失败
    public static Result error(Integer code,String msg){
       Result result = new Result();
       result.setCode(code);
       result.setMsg(msg);
       return  result;
    }
    public static Result error(){
        return error(ResultEnum.FAILURE.getCode(),ResultEnum.FAILURE.getMessage());
    }
    public static Result error(ResultEnum resultEnum){
        return error(resultEnum.getCode(),resultEnum.getMessage());
    }
    public static Result error(String msg){
        return error(ResultEnum.FAILURE.getCode(),msg);
    }
}

