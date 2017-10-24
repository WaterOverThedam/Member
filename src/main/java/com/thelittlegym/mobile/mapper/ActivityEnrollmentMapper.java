package com.thelittlegym.mobile.mapper;

import com.thelittlegym.mobile.entity.*;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Result;

import java.util.List;

/**
 * Created by TONY on 2017/10/17.
 */
public interface ActivityEnrollmentMapper {
    @Select("select status from activity_enrollment where userId=${userId} and activityId=${actId} limit 1")
    public Integer getEnrollStatus(@Param("userId") Integer userId, @Param("actId") Integer actId);
    @Select("select * from activity_enrollment where userId=${userId} and activityId=${actId} limit 1")
    public ActivityEnrollment getMyActivityEnrollment(@Param("userId") Integer userId, @Param("actId") Integer actId);

    @Select("select GROUP_CONCAT(DISTINCT u.username)phone,GROUP_CONCAT( DISTINCT CONCAT(name,'[',p.familyTitle,']'))names,p.createTime,u.addr,ifnull(u.gym,'') gym from participator p join user u on p.userId=u.id" +
            " and p.activityId=${actId} join activity_enrollment ae on ae.userId=p.userId and ae.activityId=p.activityId and ae.status=1 group by id_family")
    @Results({
            @Result(property="phones",column="phone"),
            @Result(property="participators",column="names"),
            @Result(property="addr",column="addr"),
            @Result(property="gym",column="gym"),
            @Result(property="dt",column="createTime")
    })
    public List<ParticipatorGroup> getMyActivityEnrollmentByActId(@Param("actId") Integer actId);

    @Update("update activity_enrollment ae set status=0 where not exists(select 1 from participator p where ae.userId=p.userId and ae.activityId=p.activityId) and ae.activityId=${actId}")
    public Integer updateStatusAll(@Param("actId") Integer actId);
    @Update("update activity_enrollment ae set status=1 where id=${id}")
    public Integer updateEnrollStatus(@Param("id") Integer id);

    @Select("select ifnull(a.name,'')name,a.id from activity_enrollment ae join activity a on a.id=ae.ActivityId where ae.userId=${userId} order by ae.createTime desc")
    @Results({
            @Result(id=true,property="id",column="id"),
            @Result(property="name",column="name"),
    })
    public List<Activity> getActEnroll(@Param("userId") Integer userId);
}



