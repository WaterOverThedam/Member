package com.thelittlegym.mobile.controller;

import com.thelittlegym.mobile.WebSecurityConfig;
import com.thelittlegym.mobile.entity.User;
import com.thelittlegym.mobile.service.ICouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by hibernate on 2017/6/27.
 */
@Controller
@RequestMapping("/coupon")
public class CouponCtrl {
    @Autowired
    private ICouponService couponService;
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(HttpServletRequest request, @SessionAttribute("user") User user, Model model) throws Exception {
        String tel = user.getTel();
        Map<String, Object> couponMap = couponService.getCoupon_http(tel);
        //注册优惠券
        Map<String, Object> coupon2Map = couponService.addCoupon3000(tel);

        model.addAttribute("coupon",couponMap);
        model.addAttribute("coupon2",coupon2Map);
        return "/member/coupon";
    }

    @RequestMapping(value = "/use", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> useCoupon(HttpServletRequest request,@SessionAttribute("user") User user,String code,String type) throws Exception {
        Map<String, Object> returnMap = null;
        String tel = "";
        if (user == null) {
            returnMap.put("success", false);
            returnMap.put("message", "session丢失,退出重试");
        } else {
            tel = user.getTel();
        }
//        tel = "15505152635";
        returnMap = couponService.useCoupon(tel, code,type);
        return returnMap;
    }
}
