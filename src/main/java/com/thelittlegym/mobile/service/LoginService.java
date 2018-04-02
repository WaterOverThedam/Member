package com.thelittlegym.mobile.service;

import com.thelittlegym.mobile.entity.Family;
import com.thelittlegym.mobile.entity.Result;

import java.util.Map;

/**
 * Created by hibernate on 2017/3/21.
 */
public interface LoginService {
    //登录
    public Result login(String username, String password);

    //注册
    public Result register(String username, String password, String email,Family family);

    //管理员模拟登录
    public Result login(String username);
}
