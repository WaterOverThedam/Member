package com.thelittlegym.mobile.controller;

import com.thelittlegym.mobile.WebSecurityConfig;
import com.thelittlegym.mobile.entity.Result;
import com.thelittlegym.mobile.entity.User;
import com.thelittlegym.mobile.service.ICouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by hibernate on 2017/6/27.
 */
@Controller
@RequestMapping("/coupon")
public class CouponCtrl {
    @Autowired
    private ICouponService couponService;
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(HttpServletRequest request, @SessionAttribute(WebSecurityConfig.SESSION_KEY) User user, Model model) throws Exception {
        String tel = user.getTel();
        Result couponMap = couponService.getCoupon_http(tel);
        //注册优惠券
        Result coupon2Map = couponService.addCoupon3000(tel);
        model.addAttribute("coupon",couponMap);
        model.addAttribute("coupon2",coupon2Map);
        return "/member/coupon";
    }

    @RequestMapping(value = "/use", method = RequestMethod.POST)
    @ResponseBody
    public Result useCoupon(HttpServletRequest request,@SessionAttribute("user") User user,String code,String type) throws Exception {
        return couponService.useCoupon(user.getTel(), code,type);
    }
}
