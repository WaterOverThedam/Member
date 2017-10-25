package com.thelittlegym.mobile.mapper;

import com.thelittlegym.mobile.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by TONY on 2017/10/2.
 */
public interface UserMapper {
    public List<User> getAll();
    public User getOne(Long id);
    public List<User> getParticipatorsTobe(@Param("userId") Integer userId,@Param("names") String[] names);
    @Transactional
    @Update("update user set city='${city}' where id=${userId}")
    public Integer updateCity(@Param("userId") Integer userId,@Param("city") String city);
}
