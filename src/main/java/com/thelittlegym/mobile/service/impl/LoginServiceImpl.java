package com.thelittlegym.mobile.service.impl;

import com.thelittlegym.mobile.dao.UserDao;
import com.thelittlegym.mobile.entity.User;
import com.thelittlegym.mobile.enums.ResultEnum;
import com.thelittlegym.mobile.service.ILoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hibernate on 2017/3/21.
 */
@Service
@Slf4j
public class LoginServiceImpl implements ILoginService {
    @Autowired
    private UserDao userDao;

    @Override
    public Map<String, Object> login(String username, String password){
        Map<String,Object> returnMap = new HashMap<String,Object>();
        User user = new User();
        try {
            user = userDao.findOneByUsername(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(user != null){
            if(user.getPassword().equals(password)){
                returnMap.put("value", user);
                returnMap.put("result", ResultEnum.LOGIN_SUCCESS);
            }else{
                returnMap.put("result", ResultEnum.LOGIN_WRONG_PWD);
            }
        }else{
            returnMap.put("result", ResultEnum.LOGIN_USER_NO_EXIST);
        }
        return returnMap;
    }

    @Override
    public Map<String, Object> register(String username, String password,String email,Integer idFamily)  {
        Map<String,Object> returnMap = new HashMap<String,Object>();
        User user = userDao.findOneByUsername(username);
        if(user != null){
            returnMap.put("result", ResultEnum.REGISTER_USER_EXIST);
            return returnMap;
        }else{
            user.setUsername(username);
            user.setPassword(password);
            user.setTel(username);
            user.setEmail(email);
            user.setIdFamily(idFamily);
            user.setCreateTime(new Date());
            user.setIsDelete(false);
            userDao.save(user);
            returnMap.put("value", user);
            returnMap.put("result", ResultEnum.REGISTER_SUCCESS);
            return returnMap;
        }
    }

   //后台登陆时用
    @Override
    public Map<String, Object> login(String username)  {
        Map<String,Object> returnMap = new HashMap<String,Object>();
        User user = new User();
        try {
            user = userDao.findOneByUsername(username);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        if(user != null){
                returnMap.put("value", user);
                returnMap.put("result", ResultEnum.LOGIN_SUCCESS);
        }else{
            returnMap.put("result", ResultEnum.LOGIN_USER_NO_EXIST);
        }
        return returnMap;
    }
}
