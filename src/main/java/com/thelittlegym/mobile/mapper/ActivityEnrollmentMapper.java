package com.thelittlegym.mobile.mapper;

import com.thelittlegym.mobile.entity.Activity;
import com.thelittlegym.mobile.entity.ActivityEnrollment;
import com.thelittlegym.mobile.entity.Participator;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by TONY on 2017/10/17.
 */
public interface ActivityEnrollmentMapper {
    @Select("select status from activity_enrollment where userId=${userId} and activityId=${actId} limit 1")
    public Integer getEnrollStatus(@Param("userId") Integer userId, @Param("actId") Integer actId);

    @Select("select * from activity_enrollment where userId=${userId} and activityId=${actId} limit 1")
    public ActivityEnrollment getMyActivityEnrollment(@Param("userId") Integer userId, @Param("actId") Integer actId);
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



