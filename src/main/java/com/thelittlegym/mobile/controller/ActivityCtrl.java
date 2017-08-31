package com.thelittlegym.mobile.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.thelittlegym.mobile.entity.Activity;
import com.thelittlegym.mobile.entity.Participator;
import com.thelittlegym.mobile.entity.User;
import com.thelittlegym.mobile.enums.ResultEnum;
import com.thelittlegym.mobile.service.IActivityService;
import com.thelittlegym.mobile.service.IParticipatorService;

import com.thelittlegym.mobile.utils.msg.send.ValNum;
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
@Controller
@RequestMapping("/activity")
public class ActivityCtrl {

    @Autowired
    private IActivityService activityService;
    @Autowired
    private IParticipatorService participatorService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String activities(HttpServletRequest request, Model model) throws Exception {
        HttpSession session = request.getSession();
        Object objSession = session.getAttribute("user");
        User user;
        if (objSession != null) {

        } else {
            user = (User) objSession;
        }

        return "/activity/activities";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object>  search(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                      @RequestParam(value = "size", defaultValue = "10") Integer size,
                                      @RequestParam(value = "kw", defaultValue = "") String keyword,
                                      Map<String, Object> map) throws Exception {


//TODO 权限验证
        Sort sort = new Sort(Sort.Direction.DESC.DESC, "createTime");
        Pageable pageable = new PageRequest(1, 10, sort);
        Page<Activity> activityPages = activityService.findAllByIsDeleteAndNameLike(false,keyword,pageable);

        map.put("page", activityPages);
        return  map;
    }



    @RequestMapping(value = "/myinfo", method = RequestMethod.GET)
    public String info(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        Object objSession = session.getAttribute("user");
        User user;
        if (objSession != null) {

        } else {
            user = (User) objSession;
        }

        return "/activity/myinfo";
    }





    @PostMapping("/getItems")
    @ResponseBody
    public  Map<String,Object> getItems (@RequestParam(value = "page", defaultValue = "1") Integer page,
                              @RequestParam(value = "size", defaultValue = "10") Integer size,
                              @RequestParam(value = "kw", defaultValue = "") String keyword,
                              Map<String, Object> map){
 //TODO 权限验证

        Sort sort = new Sort(Sort.Direction.DESC.DESC, "createTime");
        Pageable pageable = new PageRequest(1, 10, sort);
        Page<Activity> activityPages = activityService.findAllByIsDeleteAndNameLike(false,keyword,pageable);

        map.put("activityPage", activityPages);
        map.put("currentPage", page);
        map.put("size", size);
        return  map;
    }

    @RequestMapping(value = "/view", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject activityView(HttpServletRequest request, Integer id) throws Exception {
        HttpSession session = request.getSession();
        Object objSession = session.getAttribute("user");
        User user;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.hh");
        //TODO 权限验证
        Activity activity = activityService.findOne(id);
        JSONObject jsonObject = (JSONObject) JSON.toJSON(activity);
        jsonObject.put("beginDate", sdf.format(activity.getBeginDate()));
        jsonObject.put("endDate", sdf.format(activity.getEndDate()));
        return jsonObject;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> add(HttpServletRequest request, String actId, String name, String tel, String num) throws Exception {
        Participator p = new Participator();
        HttpSession session = request.getSession();
        Object objValMap = session.getAttribute("addValMap");
        Map<String,Object> returnMap = new HashMap<String, Object>();
        if (null != objValMap){
            Map<String,Object> valMap = (HashMap<String, Object>)objValMap;
            if (participatorService.valPar(valMap,num)){
                returnMap = participatorService.addPar(tel,name,actId);
                session.removeAttribute("addValMap");
                session.setAttribute("participator",p);
            }else{
                returnMap.put("result", ResultEnum.ENROL_CHECKSUM_ERR);
            }
        }else{
            returnMap.put("result",ResultEnum.ENROL_CHECKSUM_OVERDUE);
        }
        return returnMap;
    }

    @RequestMapping(value = "/val", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> validateNum(HttpServletRequest request, String tel) {
        ValNum valNum = new ValNum();
        HttpSession session = request.getSession();
        //发送验证码
//        Map<String,Object> returnMap = valNum.sendVal(tel);
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("success", true);
        returnMap.put("message", "2121");
        returnMap.put("timestamp", new Date());
        session.setAttribute("addValMap", returnMap);
        return returnMap;
    }

}

