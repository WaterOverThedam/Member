package com.thelittlegym.mobile.service.impl;

import com.thelittlegym.mobile.dao.AdminDao;
import com.thelittlegym.mobile.entity.Admin;
import com.thelittlegym.mobile.enums.ResultEnum;
import com.thelittlegym.mobile.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hibernate on 2017/5/19.
 */
@Service
public class AdminServiceImpl implements IAdminService {
    @Autowired
    private AdminDao adminDao;

    @Override
    public Admin login(String username, String password) {
        return adminDao.findOneByUsername(username);
    }

}
