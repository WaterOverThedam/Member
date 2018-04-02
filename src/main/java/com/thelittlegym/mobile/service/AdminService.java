package com.thelittlegym.mobile.service;

import com.thelittlegym.mobile.entity.Admin;
import com.thelittlegym.mobile.entity.Result;


/**
 * Created by hibernate on 2017/5/19.
 */
public interface AdminService {
    //登录
    public Result login(String username, String password) ;
}
