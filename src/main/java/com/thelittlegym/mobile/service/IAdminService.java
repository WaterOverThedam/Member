package com.thelittlegym.mobile.service;

import com.thelittlegym.mobile.entity.Admin;


/**
 * Created by hibernate on 2017/5/19.
 */
public interface IAdminService {
    //登录
    public Admin login(String username, String password) ;
}
