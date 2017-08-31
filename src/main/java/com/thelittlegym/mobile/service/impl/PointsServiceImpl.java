package com.thelittlegym.mobile.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.thelittlegym.mobile.common.H5Service;
import com.thelittlegym.mobile.common.OasisService;
import com.thelittlegym.mobile.dao.PointsDao;
import com.thelittlegym.mobile.dao.UserDao;
import com.thelittlegym.mobile.entity.Points;
import com.thelittlegym.mobile.entity.User;
import com.thelittlegym.mobile.service.IPointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by hibernate on 2017/6/21.
 */
@Service
public class PointsServiceImpl implements IPointsService {
    private static final String url = "http://h5.qq125.com/2017/04/coupon2/loadcoupon.php?tel=";

    @Autowired
    private PointsDao pointsDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private H5Service h5Service;
    @Autowired
    private OasisService oasisService;
    @Override
    public Map<String, Object> updatePoints_http(String tel) throws Exception {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        Integer g_points;
        Integer addPoints;
        Points p = pointsDao.findOneByTel(tel);
        User u = userDao.findOneByTel(tel);
        Integer idjt = u.getIdFamily();
        JSONArray resArr = h5Service.getByType(tel, "points");
        Integer http_num = 0;
        String zx = "";

        if (resArr != null) {
            for (Object jObject :
                    resArr) {
                JSONObject item = (JSONObject) jObject;
                if ("zx".equals(item.getString("type"))) {
                    zx = item.getString("val");

                }
                if ("points".equals(item.getString("type"))) {
                    http_num = item.getInteger("total") * 2000;
                }
            }
        } else {
            return null;
        }

        //本地已存在
        if (null != p) {
            g_points = p.getNum();
            //检查积分是否一致
            if (http_num == g_points) {
                returnMap.put("success", false);
                returnMap.put("value", p);
                returnMap.put("message", "校对一致，无需更新");
            } else {
                //更新积分
                p.setNum(http_num);
                pointsDao.save(p);
                addPoints = Math.abs(http_num - g_points);
                //TODO 增加积分
                boolean isUpdate = oasisService.addPoints(idjt.toString(),addPoints,zx);
                returnMap.put("success", true);
                returnMap.put("value", p);
                returnMap.put("message", "积分更新");
            }
        } else {
            //本地不存在
            Points p2 = new Points();
            p2.setNum(http_num);
            p2.setZx(zx);
            p2.setType("活动转介绍");
            p2.setCreateTime(new Date());
            p2.setTel(tel);
            pointsDao.save(p2);
            addPoints = http_num;
            //TODO 增加积分
            oasisService.addPoints(idjt.toString(),addPoints,zx);
        }
        return returnMap;
    }


}
