package com.thelittlegym.mobile.mapper;

import com.thelittlegym.mobile.entity.Participator;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by TONY on 2017/10/17.
 */
public interface ActivityEnrollmentMapper {
    @Select("select status from activity_enrollment where userId=${userId} and activityId=${actId} limit 1")
    public Integer getEnrollStatus(@Param("userId") Integer userId, @Param("actId") Integer actId);

}
