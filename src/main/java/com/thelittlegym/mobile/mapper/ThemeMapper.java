package com.thelittlegym.mobile.mapper;

import com.thelittlegym.mobile.entity.Theme;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;

/**
 * Created by TONY on 2018/1/31.
 */
public interface ThemeMapper {
    //使用相同的weakOfYear判断
    @Select("select id,beginDate,createTime,isShow,name,videoSrc,weekNum,course,search from theme where YEARWEEK(DATE_ADD(DATE_ADD(beginDate,INTERVAL -1 day),INTERVAL weekNum-1 week))=YEARWEEK(DATE_ADD(#{dt},INTERVAL -1 day)) and course=#{course} and isShow=#{isShow} limit 1")
    public Theme findFirstByCourseAndWeekNumAndIsShow(@Param("course") String course,@Param("dt") Date dt, @Param("isShow") Boolean isShow);

}
