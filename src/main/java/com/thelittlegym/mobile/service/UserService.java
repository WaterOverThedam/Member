package com.thelittlegym.mobile.service;


import com.thelittlegym.mobile.entity.Result;
import com.thelittlegym.mobile.entity.User;
import com.thelittlegym.mobile.enums.ResultEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * Created by hibernate on 2017/3/21.
 */
public interface UserService {
    public List<User> getUserList();
    //获取用户分页列表
    public Page<User> getUserPageList(Pageable pageable);

    //获取用户分页列表ForSearch
    public Page<User> getUserPageListForSearch(String username,Pageable pageable);

    //删除用户
    public Result deleteOneUser(Integer id);

    public User getUserById(Integer id) ;

    public Result updateUser(User user) ;

    public Long getTotal();

    public Boolean isReged(String tel);
    //前多少名是否存在此号码
    public Integer isNum(String tel, Integer num);

    public User getByTel(String tel);

}
