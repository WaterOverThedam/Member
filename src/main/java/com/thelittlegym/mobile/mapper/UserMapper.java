package com.thelittlegym.mobile.mapper;

import com.thelittlegym.mobile.entity.User;

import java.util.List;

/**
 * Created by TONY on 2017/10/2.
 */
public interface UserMapper {
    public List<User> getAll();
    public User getOne(Long id);
    public User getParticipatorsTobe(Long userId,List participator);

}
