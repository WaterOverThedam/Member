package com.thelittlegym.mobile.mapper;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;

public interface GymDisabledMapper {

    @Select("select msg from gym_disabled where gymcode=#{gymCode}")
    public String isInDisabledGym(String gymCode);
}
