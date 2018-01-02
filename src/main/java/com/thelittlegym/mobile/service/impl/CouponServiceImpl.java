package com.thelittlegym.mobile.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.thelittlegym.mobile.common.H5Service;
import com.thelittlegym.mobile.config.CouponConfig;
import com.thelittlegym.mobile.dao.CouponDao;
import com.thelittlegym.mobile.entity.Coupon;
import com.thelittlegym.mobile.entity.Result;
import com.thelittlegym.mobile.enums.ResultEnum;
import com.thelittlegym.mobile.service.ICouponService;
import com.thelittlegym.mobile.utils.ResultUtil;
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
    @Autowired
    private H5Service h5Service;
    @Autowired
    private CouponDao couponDao;
    @Autowired
    private CouponConfig couponConfig;

    @Override
    public Result getCoupon_http(String tel) {
        try {
            //是否已存一次记录，已存则不去调接口
            Coupon c = couponDao.findOneByTelAndType(tel,"1");
            if (null != c) {
                return ResultUtil.success(ResultEnum.COUPON_EXISTS,c);
            }
            tel = tel.trim();
            JSONArray couponArr = h5Service.getByType(tel, "coupon");

//          JSONObject couponObject = couponArr.getJSONObject(0);
            if (null != couponArr ) {
                //优惠券
                Coupon coupon = new Coupon();
                coupon.setCreate_time(new Date());
                coupon.setMoney(500.0f);
                coupon.setName("好朋友转介绍优惠券");
                coupon.setType("1");
                coupon.setUsed(false);
                coupon.setTel(tel);
                Coupon res = couponDao.save(coupon);

                if(res!=null) {
                    return ResultUtil.success(ResultEnum.COUPON_SYNC_SUCCESS, res);
                }else {
                    return ResultUtil.error(ResultEnum.SAVE_FAILURE);
                }
            } else {;
                return ResultUtil.error(ResultEnum.COUPON_SYNC_EMPTY);
            }
        } catch (IOException e) {
            log.error("系统错误{}",e);
        } catch (Exception e) {
            log.error("系统错误{}",e);
        }
        return ResultUtil.error();
    }

    @Override
    public Result useCoupon(String tel, String code,String type) {
        Coupon coupon = couponDao.findOneByTelAndTypeAndUsed(tel,type,false);
        //种类
        String nowCode = coupon.getType().equals("1")?couponConfig.getUseCode():couponConfig.getUseCode_2();
        if (nowCode.equals(code)) {
                if (null != coupon) {
                    coupon.setUsed(true);
                    coupon = couponDao.save(coupon);
                    if (coupon != null) {
                        return ResultUtil.success(ResultEnum.COUPON_SUCCESS_USE);
                    }else{
                        return ResultUtil.error();
                    }
                } else {
                    return ResultUtil.error(ResultEnum.COUPON_NOT_EXISTS);
                }
            } else {

              return ResultUtil.error(ResultEnum.COUPON_WRONG_NUMBER);
        }

    }

    @Override
    public Result getCoupon3000(String tel) throws Exception {
        log.info("coupon save1");
        Coupon coupon = couponDao.findOneByTelAndType(tel, "2");
        if (null != coupon) {
            return ResultUtil.success(ResultEnum.COUPON_EXISTS,coupon);
        }

        coupon = new Coupon();
        coupon.setCreate_time(new Date());
        coupon.setMoney(0.0f);
        coupon.setName("3000新用户注册");
        //2表示内部活动
        coupon.setType("2");
        coupon.setUsed(false);
        coupon.setTel(tel);
        log.info("coupon save2");
        coupon = couponDao.save(coupon);
        if (coupon != null) {
            return ResultUtil.success(ResultEnum.COUPON_SUCCESS_GET,coupon);
        }else{
            return ResultUtil.error();
        }
    }
}
