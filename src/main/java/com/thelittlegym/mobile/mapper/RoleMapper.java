package com.thelittlegym.mobile.mapper;

import com.thelittlegym.mobile.entity.Role;
import org.apache.ibatis.annotations.Select;

/**
 * Created by TONY on 2017/10/3.
 */
public interface RoleMapper {
    @Select("select * from role where id=#{id}")
    public Role getRoleById(Integer id);
}
