package com.thelittlegym.mobile.utils;


import com.thelittlegym.mobile.dao.ActivityDao;
import com.thelittlegym.mobile.dao.ThemeDao;
import com.thelittlegym.mobile.entity.Activity;
import com.thelittlegym.mobile.entity.Theme;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by TONY on 2017/8/26.
 */
public class saveUtil {
    @Autowired
    private static ThemeDao themeDao;
    @Autowired
    private static ActivityDao activityDao;

    public static void save(String menu, HttpServletRequest request, HashMap returnMap){
        System.out.println("saving...");
        switch (menu){
            case "activity":
                activitySave(request,returnMap);
                break;
            case "theme":
                themeSave(request,returnMap);
                break;
            default:
                activitySave(request,returnMap);
                break;
        }

    }
    public static void activitySave(HttpServletRequest request, HashMap returnMap){

    }

    public static void themeSave(HttpServletRequest request,HashMap returnMap){

        try {

            Theme theme = new Theme();
            theme.setId(1);
            theme.setCreateTime(toDate(request.getParameter("beginDate")));
            theme.setName(request.getParameter("name"));
            theme.setIsShow(Boolean.parseBoolean(request.getParameter("isShow")));
            theme.setWeekNum(Integer.parseInt(request.getParameter("weekNum")));
            theme.setVideoSrc(returnMap.get("path").toString());
            theme.setBeginDate(toDate(request.getParameter("beginDate")));
            theme.setIsDelete(false);
            themeDao.save(theme);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static Date toDate(String dt){
        Date date= null;
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date = sdf.parse(dt);
        }
        catch (ParseException e)
        {
            System.out.println(e.getMessage());
        }
        return date;
    }
}
