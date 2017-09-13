package com.thelittlegym.mobile.service;

import com.thelittlegym.mobile.entity.Family;

import java.util.Map;

/**
 * Created by hibernate on 2017/3/21.
 */
public interface ILoginService {
    //登录
    public Map<String,Object> login(String username, String password);

    //注册
    public Map<String,Object> register(String username, String password, String email,Family family);

    //管理员模拟登录
    public Map<String,Object> login(String username);
}
