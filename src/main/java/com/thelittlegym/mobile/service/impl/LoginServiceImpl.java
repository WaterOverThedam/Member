package com.thelittlegym.mobile.service.impl;

import com.thelittlegym.mobile.dao.UserDao;
import com.thelittlegym.mobile.entity.Family;
import com.thelittlegym.mobile.entity.Result;
import com.thelittlegym.mobile.entity.User;
import com.thelittlegym.mobile.enums.ResultEnum;
import com.thelittlegym.mobile.exception.MyException;
import com.thelittlegym.mobile.service.ILoginService;
import com.thelittlegym.mobile.utils.ResultUtil;
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
    public Result login(String username, String password){

        User  user = userDao.findOneByUsername(username);

        if(user != null){
            if(user.getPassword().equals(password)){
                return ResultUtil.success(user);
            }else{
                return ResultUtil.error(ResultEnum.LOGIN_WRONG_PWD.getCode(),ResultEnum.LOGIN_WRONG_PWD.getMessage());
            }
        }else{
                throw new MyException(ResultEnum.LOGIN_USER_NO_EXIST);
        }

    }

    @Override
    public Result register(String username, String password,String email,Family family)  {
        User res = userDao.findOneByUsername(username);
        if(res != null){
            throw new MyException(ResultEnum.REGISTER_USER_EXIST);
        }else{
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setTel(username);
            user.setEmail(email);
            user.setIdFamily(family.getId());
            user.setGym(family.getGym());
            user.setCity(family.getCity());
            user.setAddr(family.getAddr());
            user.setCreateTime(new Date());
            user.setIsDelete(false);
            User value = userDao.save(user);
            if (value==null){
                throw new MyException(ResultEnum.REGISTER_EXCEPTION);
            }else {
                return  ResultUtil.success();
            }
        }
    }

   //后台登陆时用
    @Override
    public Result login(String username)  {
        User user = new User();
        try {
            user = userDao.findOneByUsername(username);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        if(user != null){
            return ResultUtil.success(user);

        }else{
            throw new MyException(ResultEnum.LOGIN_USER_NO_EXIST);
        }
    }
}
