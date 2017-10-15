package com.thelittlegym.mobile.mapper;

import com.thelittlegym.mobile.entity.Participator;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by TONY on 2017/10/15.
 */
public interface ParticipatorMapper {

    @Select("select * from participator where userId=${userId} and activityId=${actId}")
    public List<Participator> getParticipatorByUserAndActivity(@Param("userId") Integer userId, @Param("actId") Integer actId);

}
