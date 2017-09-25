package com.thelittlegym.mobile.dao;

import com.thelittlegym.mobile.entity.Activity;
import com.thelittlegym.mobile.entity.PageLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by TONY on 2017/9/24.
 */
public interface PageLogDao  extends JpaRepository<PageLog,Integer> {
}
