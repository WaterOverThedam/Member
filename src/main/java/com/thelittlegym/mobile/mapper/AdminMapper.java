package com.thelittlegym.mobile.mapper;

import com.thelittlegym.mobile.entity.Admin;
import com.thelittlegym.mobile.entity.PageLogGroup;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by TONY on 2017/10/3.
 */
public interface AdminMapper {

    @Select("select id,username,password,roleId from admin where username=#{username} and isDelete=0 limit 1")
    @Results({
            @Result(id=true,property="id",column="id"),
            @Result(property="username",column="username"),
            @Result(property="password",column="password"),
            @Result(property="role",column="roleId",one=@One(select="com.thelittlegym.mobile.mapper.RoleMapper.getRoleById"))
    })
    public  Admin getAdminByUsername(String username);
}
