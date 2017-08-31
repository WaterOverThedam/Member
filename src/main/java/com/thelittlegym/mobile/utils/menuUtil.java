package com.thelittlegym.mobile.utils;

/**
 * Created by TONY on 2017/8/25.
 */
public class menuUtil {
    public static String getNameById(String Id){
        switch (Id){
            case "activity":
                return "活动";
            case "theme":
                return "课程亮点";
            default:
                return "活动";
        }
    }
}
