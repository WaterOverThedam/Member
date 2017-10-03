package com.thelittlegym.mobile.dao;


import com.thelittlegym.mobile.entity.PageLog;
import com.thelittlegym.mobile.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * Created by TONY on 2017/9/24.
 */
public interface PageLogDao  extends JpaRepository<PageLog,Integer> {
    Page<PageLog> findAllBySearchIsLike(String keyWord, Pageable pageable);
    Page<PageLog> findAllBySearchIsLikeAndCreateTimeBetween(String keyWord,Date min,Date max,Pageable pageable);
}
