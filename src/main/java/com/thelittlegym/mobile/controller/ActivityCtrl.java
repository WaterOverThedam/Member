package com.thelittlegym.mobile.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.thelittlegym.mobile.WebSecurityConfig;
import com.thelittlegym.mobile.entity.*;
import com.thelittlegym.mobile.enums.ResultEnum;
import com.thelittlegym.mobile.service.IActivityService;
import com.thelittlegym.mobile.service.IParticipatorService;

import com.thelittlegym.mobile.utils.ResultUtil;
import com.thelittlegym.mobile.utils.msg.send.ValNum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hibernate on 2017/5/3.
 */
@Slf4j
@RestController
@RequestMapping("/activity")
public class ActivityCtrl {

    @Autowired
    private IActivityService activityService;
    @Autowired
    private IParticipatorService participatorService;



    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result  search(@RequestParam(value = "page", defaultValue = "1") Integer pageNow,
                                      @RequestParam(value = "size", defaultValue = "10") Integer size,
                                      @RequestParam(value = "kw", defaultValue = "") String keyword) throws Exception {

        Sort sort = new Sort(Sort.Direction.DESC.DESC, "createTime");
        Pageable pageable = new PageRequest(pageNow-1, 10, sort);
        Page<Activity> activityPages = activityService.findAllByIsDeleteAndSearchLike(false,'%'+keyword+'%',pageable);

        return ResultUtil.success(activityPages);
    }



    @RequestMapping("/getItems")
    public Result getItems (@RequestParam(value = "page", defaultValue = "1") Integer pageNow,
                            @RequestParam(value = "size", defaultValue = "10") Integer size,
                            @RequestParam(value = "kw", defaultValue = "") String keyword){

        Sort sort = new Sort(Sort.Direction.DESC.DESC, "createTime");
        Pageable pageable = new PageRequest(pageNow-1, 10, sort);
        Page<Activity> activityPages = activityService.findAllByIsDeleteAndSearchLike(false,'%'+keyword+'%',pageable);
//        Map<String, Object> returnMap = new HashMap<String, Object>();
//        returnMap.put("activity",activityPages);
//        returnMap.put("user",user);
        return ResultUtil.success(activityPages);
    }

    @RequestMapping(value = "/view", method = RequestMethod.POST)
    public Activity activityView(Integer id) throws Exception {
        if (id!=null) {
            return activityService.findOne(id);
        }else{
            return null;
        }
    }

//    @RequestMapping(value = "/add", method = RequestMethod.POST)
//    public Result add(HttpServletRequest request, String actId, String name, String tel, String num) throws Exception {
//        Result res = null;
//        HttpSession session = request.getSession();
//        Object objValMap = session.getAttribute("addValMap");
//
//        if (null != objValMap){
//            Map<String,Object> valMap = (HashMap<String, Object>)objValMap;
//            if (participatorService.valPar(valMap,num)){
//                res = participatorService.addPar(tel,name,actId);
//                session.removeAttribute("addValMap");
//                session.setAttribute("participator",res.getData());
//            }else{
//                return ResultUtil.error(ResultEnum.ENROL_CHECKSUM_ERR);
//            }
//        }else{
//            return ResultUtil.error(ResultEnum.ENROL_CHECKSUM_OVERDUE);
//        }
//        return res;
//    }

    @RequestMapping(value = "/val", method = RequestMethod.POST)
    public Map<String, Object> validateNum(HttpServletRequest request, String tel) {
        ValNum valNum = new ValNum();
        HttpSession session = request.getSession();
        //发送验证码
//      Map<String,Object> returnMap = valNum.sendVal(tel);
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("success", true);
        returnMap.put("message", "2121");
        returnMap.put("timestamp", new Date());
        session.setAttribute("addValMap", returnMap);
        return returnMap;
    }

}

