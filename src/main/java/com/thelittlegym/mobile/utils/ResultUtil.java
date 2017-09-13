package com.thelittlegym.mobile.utils;

import com.thelittlegym.mobile.entity.Result;

/**
 * Created by Tony on 2017/9/13.
 */
public class ResultUtil{
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
    public static Result success(String msg){
        return success(0,msg,null);
    }
    public static Result success(){
        return success(0,"操作成功",null);
    }
   public static Result error(Integer code,String msg){
       Result result = new Result();
       result.setCode(code);
       result.setMsg(msg);
       return  result;
   }
}

