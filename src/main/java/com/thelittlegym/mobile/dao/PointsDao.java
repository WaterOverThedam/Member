package com.thelittlegym.mobile.dao;

import com.thelittlegym.mobile.entity.Activity;
import com.thelittlegym.mobile.entity.Points;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by TONY on 2017/8/26.
 */
public interface PointsDao extends JpaRepository<Points,Integer> {
    public Points findOneByTel(String tel);
}
