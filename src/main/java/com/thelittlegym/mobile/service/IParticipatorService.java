package com.thelittlegym.mobile.service;

import com.thelittlegym.mobile.entity.Activity;
import com.thelittlegym.mobile.entity.Participator;
import com.thelittlegym.mobile.entity.Result;
import com.thelittlegym.mobile.entity.User;

import java.util.List;
import java.util.Map;

/**
 * Created by hibernate on 2017/5/26.
 */
public interface IParticipatorService {
    //参与活动
    public Result addPar(String tel, String name, Integer actid,User user);
    public Result enroll(Integer actId,User user);
    //修改信息
    public void updatePar(Participator p);
    //验证码是否通过
    public Boolean valPar(Map<String,Object> sendMap,String reqNum);

}
