package com.thelittlegym.mobile.controller;

import com.alibaba.fastjson.JSONObject;
import com.thelittlegym.mobile.WebSecurityConfig;
import com.thelittlegym.mobile.common.H5Service;
import com.thelittlegym.mobile.entity.Prize;
import com.thelittlegym.mobile.entity.Result;
import com.thelittlegym.mobile.entity.User;
import com.thelittlegym.mobile.mapper.PrizeMapper;
import com.thelittlegym.mobile.service.CouponService;
import com.thelittlegym.mobile.service.PointsService;
import com.thelittlegym.mobile.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Created by hibernate on 2017/6/27.
 */
@Controller
@Slf4j
@RequestMapping("/coupon")
public class CouponCtrl {
    @Autowired
    private CouponService couponService;
    @Autowired
    private PointsService pointsService;
    @Autowired
    private H5Service h5Service;
    @Autowired
    private PrizeMapper prizeMapper;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(HttpServletRequest request, @SessionAttribute(WebSecurityConfig.SESSION_KEY) User user,Integer pointed, Model model) throws Exception {
        String tel = user.getUsername();
        Map<String, Object> res = h5Service.getAll(tel);
        Result pointMap = ResultUtil.error();
        System.out.println("res"+res);
        if(res!=null) {
            if (res.get("coupon") != null) {
                Integer num = Integer.parseInt((String) res.get("coupon"));
                if (num > 0) {
                    couponService.updateCoupon_http(tel);
                }
            }
            if (res.get("points") != null && Integer.parseInt((String) res.get("points")) > 0) {
                Integer http_num = Integer.parseInt((String) res.get("points"));
                Integer diff = http_num - pointed;
                //log.info("diff:{}",diff.toString());
                if (diff > 0) {
                    Result pointAdd = pointsService.updatePoints_http(tel, http_num, pointed, (String) res.get("zx"));
                    model.addAttribute("pointAdd", pointAdd);
                }
            }
            if (res.get("prize") != null) {
                String str = (String) res.get("prize");
                log.info("shit:{}",str);
                if (str != null && !str.equals("") && !str.equals("[]")) {
                    List<Prize> prizeList = JSONObject.parseArray(str, Prize.class);
                    //JSONObject jsonObject = JSONObject.parseObject(str);
                    prizeList.stream().map(item -> {
                        item.setTel(tel);
                        item.setUsed(false);
                        return item;
                    }).collect(Collectors.toList());
                    prizeMapper.addPrizeBatch(prizeList);
                }
            }
        }
        List<Prize> prizeList = prizeMapper.getPrizes(tel);
        //抵用券
        Result couponMap = couponService.getCoupon(tel,"1");
        model.addAttribute("coupon", couponMap);
        //积分
        model.addAttribute("pointAdd",pointMap);
        //注册优惠券
        HttpSession session = request.getSession();
        Integer in3000 =(Integer) session.getAttribute("in3000");
        Result coupon2Map = ResultUtil.success();
        if(in3000>0) {
             coupon2Map = couponService.getCoupon3000(tel);
        }
        System.out.println(prizeList);
        model.addAttribute("coupon3",prizeList);
        model.addAttribute("coupon2",coupon2Map);
        return "/member/coupon";
    }

    @RequestMapping(value = "/use", method = RequestMethod.POST)
    @ResponseBody
    public Result useCoupon(HttpServletRequest request,@SessionAttribute("user") User user,String code,String type,Integer id) throws Exception {
        return couponService.useCoupon(user.getTel(), code,type,id);
    }
}
