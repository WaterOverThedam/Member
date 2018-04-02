package com.thelittlegym.mobile.service.impl;

import com.thelittlegym.mobile.entity.Admin;
import com.thelittlegym.mobile.entity.Result;
import com.thelittlegym.mobile.enums.ResultEnum;
import com.thelittlegym.mobile.mapper.AdminMapper;
import com.thelittlegym.mobile.service.AdminService;
import com.thelittlegym.mobile.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by hibernate on 2017/5/19.
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Result login(String username, String password) {

        Admin admin = adminMapper.getAdminByUsername(username);
        if (admin != null && admin.getPassword().equals(password.trim())) {
            return ResultUtil.success(ResultEnum.LOGIN_SUCCESS,admin);
        } else {
            return ResultUtil.error(ResultEnum.LOGIN_WRONG_PWD);
        }

    }
}