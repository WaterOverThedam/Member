package com.thelittlegym.mobile.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.thelittlegym.mobile.common.H5Service;
import com.thelittlegym.mobile.common.OasisService;
import com.thelittlegym.mobile.dao.PointsDao;
import com.thelittlegym.mobile.dao.UserDao;
import com.thelittlegym.mobile.entity.Points;
import com.thelittlegym.mobile.entity.Result;
import com.thelittlegym.mobile.entity.User;
import com.thelittlegym.mobile.service.IPointsService;
import com.thelittlegym.mobile.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by hibernate on 2017/6/21.
 */
@Service
@Slf4j
public class PointsServiceImpl implements IPointsService {

    @Autowired
    private PointsDao pointsDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private OasisService oasisService;

    public Result updatePoints_http(String tel, Integer http_num, Integer pointed,String zx) throws Exception {
        Points p = pointsDao.findOneByTel(tel);
        //本地留存
        if (null == p) {
           //本地不存在创建
            Points p2 = new Points();
            p2.setNum(http_num);
            p2.setZx(zx);
            p2.setType("活动转介绍");
            p2.setCreateTime(new Date());
            p2.setTel(tel);
            pointsDao.save(p2);
        }else{
         //更新积分
            p.setNum(http_num);
            pointsDao.save(p);
        }
        //oasis同步
        if (http_num > pointed) {
            Integer addPoints = (http_num - pointed)* 2000;
            //TODO 增加积分
            User u = userDao.findOneByTel(tel);
            Integer idjt = u.getIdFamily();
            boolean isUpdate = oasisService.addPoints(idjt.toString(),addPoints,zx);
            return  isUpdate? ResultUtil.success(addPoints):ResultUtil.error("同步失败");
        }  else{
            return ResultUtil.error("校对一致，无需更新");
        }

    }


}
