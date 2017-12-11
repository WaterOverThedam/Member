package com.thelittlegym.mobile.mapper;

import com.thelittlegym.mobile.entity.Setting;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * Created by TONY on 2017/12/8.
 */
public interface SettingMapper {
    @Select("select name,content from setting where name=#{name}")
    public Setting getSettingByName(String name);
    @Insert("insert setting(content,name) values(#{content},#{name})")
    public Integer insert(Setting setting);
    @Update("update setting set content = #{content} where name=#{name}")
    public Integer update(Setting setting);
}
