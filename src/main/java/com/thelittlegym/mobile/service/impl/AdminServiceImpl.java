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
    public Map<String, Object> login(String username, String password) {
        HashMap<String,Object> returnMap = new HashMap<String,Object>();
        Admin admin = adminDao.findOneByUsername(username);
        if (null != admin){
            if(password.equals(admin.getPassword())){
                returnMap.put("value", admin);
                returnMap.put("result", ResultEnum.LOGIN_SUCCESS);
            }else{
                returnMap.put("result", ResultEnum.LOGIN_WRONG_PWD);
            }
        }else{
            returnMap.put("result", ResultEnum.LOGIN_USER_NO_EXIST);
        }
        return returnMap;
    }

}
