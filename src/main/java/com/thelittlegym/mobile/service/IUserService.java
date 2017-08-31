package com.thelittlegym.mobile.service;


import com.thelittlegym.mobile.entity.User;
import com.thelittlegym.mobile.enums.ResultEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * Created by hibernate on 2017/3/21.
 */
public interface IUserService {
    //获取用户分页列表
    public Page<User> getUserPageList(Pageable pageable);

    //获取用户分页列表ForSearch
    public Page<User> getUserPageListForSearch(String username,Pageable pageable);

    //删除用户
    public ResultEnum deleteOneUser(Integer id);

    public User getUserById(Integer id) ;

    public void updateUser(User user) ;

    public Long getTotal();

    public Boolean isReged(String tel);
    //前多少名是否存在此号码
    public Boolean isNum(String tel, Integer num);

    public User getByTel(String tel);

}
