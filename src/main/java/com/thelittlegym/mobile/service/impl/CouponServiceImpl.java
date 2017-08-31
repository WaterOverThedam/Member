package com.thelittlegym.mobile.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.thelittlegym.mobile.common.H5Service;
import com.thelittlegym.mobile.dao.CouponDao;
import com.thelittlegym.mobile.entity.Coupon;
import com.thelittlegym.mobile.service.ICouponService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hibernate on 2017/6/16.
 */
@Service
@Slf4j
public class CouponServiceImpl implements ICouponService {
    private static final String useCode = "Sw6cWT88";
    private static final String useCode_2 = "Jag2xG6D";
    @Autowired
    private H5Service h5Service;
    @Autowired
    private CouponDao couponDao;

    @Override
    public Map<String, Object> getCoupon_http(String tel) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        try {
            //是否已存，已存则不去调接口
            Coupon c = couponDao.findOneByTelAndType(tel,"1");
            if (null != c) {
                returnMap.put("value", c);
                returnMap.put("success", true);
                returnMap.put("message", "存在");
                return returnMap;
            }
            tel = tel.trim();
            JSONArray couponArr = h5Service.getByType(tel, "coupon");
//            JSONObject couponObject = couponArr.getJSONObject(0);
            if (null != couponArr ) {
                //优惠券
                Coupon coupon = new Coupon();
                coupon.setCreate_time(new Date());
                coupon.setMoney(500.0f);
                coupon.setName("好朋友转介绍优惠券");
                coupon.setType("1");
                coupon.setUsed(false);
                coupon.setTel(tel);
                couponDao.save(coupon);
                returnMap.put("value", coupon);
                returnMap.put("success", true);
                returnMap.put("message", "第一次存入");
                return returnMap;
            } else {
                returnMap.put("success", false);
                returnMap.put("message", "返回结果为空");
                return returnMap;
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public Map<String, Object> useCoupon(String tel, String code,String type) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        Coupon coupon = couponDao.findOneByTelAndTypeAndUsed(tel,type,false);
        //种类
        String nowCode = coupon.getType().equals("1")?useCode:useCode_2;
        if (nowCode.equals(code)) {
                if (null != coupon) {
                    coupon.setUsed(true);
                    couponDao.save(coupon);
                    returnMap.put("success", true);
                    returnMap.put("message", "使用成功");
                } else {
                    returnMap.put("success", false);
                    returnMap.put("message", "该用户没有优惠券");
                }
            } else {
                returnMap.put("success", false);
                returnMap.put("message", "核销码错误");
        }
        return returnMap;
    }

    @Override
    public Map<String, Object> addCoupon3000(String tel) throws Exception {
        //临时添加  活动开始时间为8-17 8点
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String beginDateStr = "2017-08-17 08:00:00";
        String endDateStr = "2017-09-10 23:59:59";
        Date beginDate = sdf.parse(beginDateStr);
        Date endDate = sdf.parse(endDateStr);
        if ( now.getTime() < beginDate.getTime() || now.getTime() > endDate.getTime()){
            return null;
        }

        Map<String, Object> returnMap = new HashMap<String, Object>();
        Coupon c = couponDao.findOneByTelAndType(tel,"1");
        if (null != c ){
            returnMap.put("value",c);
            returnMap.put("success",false);
            returnMap.put("message","已领取");
            return returnMap;
        }
        Coupon coupon = new Coupon();
        coupon.setCreate_time(new Date());
        coupon.setMoney(0.0f);
        coupon.setName("3000新用户注册");
        //2表示内部活动
        coupon.setType("2");
        coupon.setUsed(false);
        coupon.setTel(tel);
        couponDao.save(coupon);
        returnMap.put("value",coupon);
        returnMap.put("success",true);
        returnMap.put("message","领取成功");
        return returnMap;
    }
}
