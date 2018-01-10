package com.thelittlegym.mobile.controller;

import com.alibaba.fastjson.JSONObject;
import com.thelittlegym.mobile.WebSecurityConfig;
import com.thelittlegym.mobile.common.H5Service;
import com.thelittlegym.mobile.entity.GymClass;
import com.thelittlegym.mobile.entity.Prize;
import com.thelittlegym.mobile.entity.Result;
import com.thelittlegym.mobile.entity.User;
import com.thelittlegym.mobile.mapper.PrizeMapper;
import com.thelittlegym.mobile.service.ICouponService;
import com.thelittlegym.mobile.service.IPointsService;
import com.thelittlegym.mobile.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
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
    private ICouponService couponService;
    @Autowired
    private IPointsService pointsService;
    @Autowired
    private H5Service h5Service;
    @Autowired
    private PrizeMapper prizeMapper;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public String index(HttpServletRequest request, @SessionAttribute(WebSecurityConfig.SESSION_KEY) User user,Integer pointed, Model model) throws Exception {
        String tel = user.getTel();
        Map<String, Object> res = h5Service.getAll(tel);
        //System.out.println(res);
        if(res.get("coupon")!=null) {
            String num = (String)res.get("coupon");
            if( Integer.parseInt(num)>0) {
                Result couponMap = couponService.updateCoupon_http(tel);
                model.addAttribute("coupon", couponMap);
            }
        }
        if(res.get("points")!=null && Integer.parseInt((String) res.get("points"))>0) {
            Result pointAdd = pointsService.updatePoints_http(tel, pointed, (String) res.get("zx"));
            model.addAttribute("pointAdd",pointAdd);
        }else {
            model.addAttribute("pointAdd",ResultUtil.error());
        }

        if(res.get("prize")!=null) {
             String str = (String)res.get("prize");
             if(str != null && !str.equals("")){
                 List<Prize> prizeList = JSONObject.parseArray(str, Prize.class);
                 //JSONObject jsonObject = JSONObject.parseObject(str);
                 prizeList.stream().map(item -> {
                     item.setTel(tel);
                     return item;
                 }).collect(Collectors.toList());
                 System.out.println(prizeList);
                 prizeMapper.addPrizeBatch(prizeList);

             }
        }
        HttpSession session = request.getSession();
       //注册优惠券
        Integer in3000 =(Integer) session.getAttribute("in3000");
        Result coupon2Map = ResultUtil.success();
        if(in3000>0) {
             coupon2Map = couponService.getCoupon3000(tel);
        }

        model.addAttribute("coupon2",coupon2Map);
return "abc";
        //return "/member/coupon";
    }

    @RequestMapping(value = "/use", method = RequestMethod.POST)
    @ResponseBody
    public Result useCoupon(HttpServletRequest request,@SessionAttribute("user") User user,String code,String type) throws Exception {
        return couponService.useCoupon(user.getTel(), code,type);
    }
}
