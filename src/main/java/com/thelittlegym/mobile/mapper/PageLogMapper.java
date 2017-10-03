package com.thelittlegym.mobile.mapper;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.thelittlegym.mobile.entity.PageLogGroup;
import com.thelittlegym.mobile.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * Created by TONY on 2017/10/1.
 */
public interface PageLogMapper {
    @Select("select ${groups},count(1)ci from pagelog GROUP BY ${groups}")
    public List<PageLogGroup> getPageLogStat(@Param("groups")String groups);
    @Select("select ${groups},count(1)ci from pagelog where createTime between '${dtBegin}' and '${dtEnd}' GROUP BY ${groups}")
    public List<PageLogGroup> getPageLogStatByCreatTime(@Param("groups")String groups, @Param("dtBegin")String dtBegin, @Param("dtEnd")String dtEnd);
}
