package com.thelittlegym.mobile.controller;

import com.thelittlegym.mobile.WebSecurityConfig;
import com.thelittlegym.mobile.entity.Result;
import com.thelittlegym.mobile.entity.User;
import com.thelittlegym.mobile.service.ICouponService;
import com.thelittlegym.mobile.service.IPointsService;
import com.thelittlegym.mobile.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * Created by hibernate on 2017/6/27.
 */
@Controller
@Slf4j
@RequestMapping("/coupon")
public class CouponCtrl {
    @Autowired
    private ICouponService couponService;
    @Autowired
    private IPointsService pointsService;
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(HttpServletRequest request, @SessionAttribute(WebSecurityConfig.SESSION_KEY) User user,Integer pointed, Model model) throws Exception {
        String tel = user.getTel();

        Result couponMap = couponService.getCoupon_http(tel);
        Result pointAdd =pointsService.updatePoints_http(tel,pointed);
        HttpSession session = request.getSession();
       //注册优惠券
        Integer in3000 =(Integer) session.getAttribute("in3000");
        Result coupon2Map = ResultUtil.success();
        if(in3000>0) {
             coupon2Map = couponService.getCoupon3000(tel);
        }
        model.addAttribute("coupon2",coupon2Map);
        model.addAttribute("coupon",couponMap);
        model.addAttribute("pointAdd",pointAdd);
        return "/member/coupon";
    }

    @RequestMapping(value = "/use", method = RequestMethod.POST)
    @ResponseBody
    public Result useCoupon(HttpServletRequest request,@SessionAttribute("user") User user,String code,String type) throws Exception {
        return couponService.useCoupon(user.getTel(), code,type);
    }
}
