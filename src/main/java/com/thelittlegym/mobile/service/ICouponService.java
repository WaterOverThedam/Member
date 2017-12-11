package com.thelittlegym.mobile.service;

import com.thelittlegym.mobile.entity.Result;

import java.util.Map;

/**
 * Created by hibernate on 2017/6/16.
 */
public interface ICouponService {
    //查询外部接口是否有优惠券，有则查询本地是否存储，存储则查询是否使用
    public Result getCoupon_http(String tel) ;

    public Result useCoupon(String tel,String code,String type) ;

    public Result getCoupon3000(String tel) throws Exception ;
}
