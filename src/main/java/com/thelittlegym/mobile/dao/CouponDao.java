package com.thelittlegym.mobile.dao;

import com.thelittlegym.mobile.entity.Activity;
import com.thelittlegym.mobile.entity.Coupon;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by TONY on 2017/8/26.
 */
public interface CouponDao extends JpaRepository<Coupon,Integer> {
    public Coupon findOneByTelAndType(String tel,String type);
    public Coupon findOneByTelAndTypeAndUsed(String tel,String type,Boolean used);
}



