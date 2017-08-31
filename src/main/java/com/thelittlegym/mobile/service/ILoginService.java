package com.thelittlegym.mobile.service;

import java.util.Map;

/**
 * Created by hibernate on 2017/3/21.
 */
public interface ILoginService {
    //登录
    public Map<String,Object> login(String username, String password);

    //注册
    public Map<String,Object> register(String username, String password, String email, Integer idFamily);

    //管理员模拟登录
    public Map<String,Object> login(String username);
}
