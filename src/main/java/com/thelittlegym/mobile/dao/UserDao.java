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

    @Query(value="select count(1) from User u where gym not like '月球%'and gym not like '火星%' and gym not like 'head%' and gym not like '测试%' and gym like '%中心'",nativeQuery = true)
    Long findCount();

    public Page<User> findAllByUsernameLike(String keyWord,Pageable pageable);
    public User findOneByUsername(String username);

}
