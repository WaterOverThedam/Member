package com.thelittlegym.mobile.dao;

import com.thelittlegym.mobile.entity.Admin;
import com.thelittlegym.mobile.entity.BlackList;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by TONY on 2017/9/25.
 */
public interface BlackListDao extends JpaRepository<BlackList,String> {
}
