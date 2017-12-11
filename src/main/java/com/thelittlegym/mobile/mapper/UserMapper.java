package com.thelittlegym.mobile.mapper;

import com.thelittlegym.mobile.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by TONY on 2017/10/2.
 */
public interface UserMapper {
    public List<User> getAll(@Param("search") String search);
    public User getOne(Long id);
    public List<User> getParticipatorsTobe(@Param("userId") Integer userId,@Param("names") String[] names);
    @Transactional
    @Update("update user set city='${city}' where id=${userId}")
    public Integer updateCity(@Param("userId") Integer userId,@Param("city") String city);

    @Select("SELECT count(id) total FROM (SELECT @ROW := @ROW + 1 AS ROW,u.* FROM (SELECT * FROM USER u WHERE create_time >='${dtstart}' ORDER BY create_time) u, (SELECT @ROW := 0) r WHERE @ROW <${num}) res WHERE res.telephone ='${tel}'")
    BigInteger getTopReg(@Param("num") Integer num, @Param("tel") String  tel, @Param("dtstart") String dtstart);

    public List<HashMap> getStats();
}
