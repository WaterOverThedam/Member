package com.thelittlegym.mobile.service;

import com.thelittlegym.mobile.entity.Result;

import java.util.Map;

/**
 * Created by hibernate on 2017/6/21.
 */
public interface PointsService {
    //查询外部接口是否有优惠券，有则查询本地是否存储，存储则查询是否使用
    public Result updatePoints_http(String tel, Integer http_num,Integer pointed,String zx) throws Exception ;
}
