package com.thelittlegym.mobile.service;

import java.util.Map;

/**
 * Created by hibernate on 2017/5/19.
 */
public interface IAdminService {
    //登录
    public Map<String,Object> login(String username, String password) ;
}
