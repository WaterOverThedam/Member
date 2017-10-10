package com.thelittlegym.mobile.controller;

import com.thelittlegym.mobile.WebSecurityConfig;
import com.thelittlegym.mobile.dao.ActivityEnrollmentDao;
import com.thelittlegym.mobile.entity.*;
import com.thelittlegym.mobile.enums.ResultEnum;
import com.thelittlegym.mobile.mapper.UserMapper;
import com.thelittlegym.mobile.service.IActivityService;
import com.thelittlegym.mobile.service.IParticipatorService;

import com.thelittlegym.mobile.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
    @Autowired
    private ActivityEnrollmentDao activityEnrollmentDao;
    @Autowired
    private UserMapper userMapper;



    @RequestMapping(value = "/checkEnrol")
    public Result checkEnrolStatus (@SessionAttribute(WebSecurityConfig.SESSION_KEY) User user,@RequestParam(value = "actId",required = false) Integer actId) throws Exception {
        Activity activity = new Activity();
        activity.setId(actId);
        ActivityEnrollment activityEnrollment = activityEnrollmentDao.findFirstByUserAndActivity(user,activity);
        return ResultUtil.success(activityEnrollment.getStatus());
    }


    @RequestMapping("/getItems")
    public Result getItems (@RequestParam(value = "pageNow", defaultValue = "1") Integer pageNow,
                            @RequestParam(value = "size", defaultValue = "4") Integer size,
                            @RequestParam(value = "kw", defaultValue = "") String kw){
        //log.info(pageNow.toString());
        Sort sort = new Sort(Sort.Direction.DESC.DESC, "createTime");
        Pageable pageable = new PageRequest(pageNow-1, size, sort);
        Page<Activity> activityPages = activityService.findAllByIsDeleteAndSearchLike(false,'%'+kw+'%',pageable);
        return ResultUtil.success(activityPages);
    }

//    @RequestMapping(value = "/view", method = RequestMethod.POST)
//    public Activity activityView(Integer id) throws Exception {
//        if (id!=null) {
//            return activityService.findOne(id);
//        }else{
//            return null;
//        }
//    }

    @RequestMapping(value = "/getPar", method = RequestMethod.POST)
    public User getPar(Long userId, List names) throws Exception {
        if (userId!=null) {
            return userMapper.getParticipatorsTobe(userId,names);
        }else{
            return null;
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result add(@SessionAttribute(WebSecurityConfig.SESSION_KEY) User user,Integer actId, String name, String tel) throws Exception {
           return participatorService.addPar(tel,name,actId,user);
    }


    @RequestMapping(value = "/enrol", method = RequestMethod.POST)
    public Result enrol(@SessionAttribute(WebSecurityConfig.SESSION_KEY) User user,Integer actId) throws Exception {
        return participatorService.enroll(actId,user);
    }

}

