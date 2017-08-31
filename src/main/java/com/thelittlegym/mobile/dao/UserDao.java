package com.thelittlegym.mobile.dao;

import com.thelittlegym.mobile.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.util.Date;

/**
 * Created by TONY on 2017/8/26.
 */
public interface UserDao extends JpaRepository<User,Integer> {
    public User findOneByTel(String tel);
    //获取用户分页列表
    @Query(value="select user from User user where user.isDelete=0 and user.createTime <= :dtEnd and user.createTime >= :dtBegin AND user.tel <> '18917353367' AND user.tel <> '15949190026' ")
    Page<User> getUserPageListByDate(@Param("dtBegin") Date dtBegin,@Param("dtEnd") Date dtEnd,Pageable pageable);

    @Query(value="select count(1) from User u where u.username != '15949190026' and u.username != '18917353367'",nativeQuery = true)
    Long findCount();
    @Query(value ="SELECT count(id) total FROM (SELECT @ROW := @ROW + 1 AS ROW,u.* FROM (SELECT * FROM USER u WHERE  AND telephone != '15949190026' AND telephone != '18917353367' ORDER BY create_time) u, (SELECT @ROW := 0) r WHERE @ROW < :num) res WHERE res.telephone = :tel",nativeQuery=true)
    BigInteger findCountBySql(@Param("num") Integer num,@Param("tel") String  tel);

    public Page<User> findAllByUsernameLike(String keyWord,Pageable pageable);
    public User findOneByUsername(String username);

}
