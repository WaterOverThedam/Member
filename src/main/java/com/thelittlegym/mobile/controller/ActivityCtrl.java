package com.thelittlegym.mobile.controller;

import com.thelittlegym.mobile.WebSecurityConfig;
import com.thelittlegym.mobile.dao.ActivityEnrollmentDao;
import com.thelittlegym.mobile.dao.ParticipatorDao;
import com.thelittlegym.mobile.dao.UserDao;
import com.thelittlegym.mobile.entity.*;
import com.thelittlegym.mobile.enums.ResultEnum;
import com.thelittlegym.mobile.mapper.ActivityEnrollmentMapper;
import com.thelittlegym.mobile.mapper.ParticipatorMapper;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    private ParticipatorDao participatorDao;
    @Autowired
    private ParticipatorMapper participatorMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ActivityEnrollmentMapper activityEnrollmentMapper;
    @Autowired
    private UserDao userDao;
    @RequestMapping(value = "/checkEnrol")
    public Integer checkEnrolStatus (Integer userId,Integer actId) throws Exception {
        return activityEnrollmentMapper.getEnrollStatus(userId, actId);
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

    @RequestMapping(value = "/view", method = RequestMethod.POST)
    public Activity activityView(Integer id) throws Exception {
        if (id!=null) {
            return activityService.findOne(id);
        }else{
            return null;
        }
    }

    @RequestMapping(value = "/getCandidate")
    public List<User> getCandidate(@RequestParam(value = "userId") Integer userId, @RequestParam(value = "names[]" ,required = false) String[] names) throws Exception {

        return userMapper.getParticipatorsTobe(userId,names);

    }

    @RequestMapping(value = "/getPar")
    public List<Participator> getPar(@RequestParam(value = "userId") Integer userId,@RequestParam(value = "actId",required = false)Integer actId) throws Exception {
      if(actId==null){
          return null;
      }
      return participatorMapper.getParticipatorByUserAndActivity(userId,actId);

    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result add(@SessionAttribute(WebSecurityConfig.SESSION_KEY) User user,Integer actId, String name, String tel, String familyTitle) throws Exception {
        log.info(actId.toString());
        return participatorService.addPar(tel,name,actId,user,familyTitle);
    }

    @RequestMapping(value = "/delPar")
    public Result delPar(Integer idPar) throws Exception {
        Participator participator = participatorDao.findOne(idPar);
        try {
            participatorDao.delete(idPar);
        }catch (Exception e){
            return ResultUtil.error(e.getMessage());
        }
        activityEnrollmentMapper.updateStatusAll(participator.getActivity().getId());
        return ResultUtil.success();
    }

    @RequestMapping(value = "/enroll")
    public Result enroll(@SessionAttribute(WebSecurityConfig.SESSION_KEY) User user,Integer actId) throws Exception {
        return participatorService.enroll(actId,user);
    }

    @GetMapping(value = "/updateCity")
    public Result saveUser(HttpServletRequest request,@SessionAttribute(WebSecurityConfig.SESSION_KEY) User user,String city,Integer userId) throws Exception {
        //更新session
         HttpSession session = request.getSession();
         user.setCity(city);
         session.setAttribute(WebSecurityConfig.SESSION_KEY,user);
         //更新表
         userMapper.updateCity(userId,city);
         return ResultUtil.success();
    }

    @GetMapping(value = "/getActEnroll")
    public List<Activity> getActEnroll(Integer userId) throws Exception {
        return activityEnrollmentMapper.getActEnroll(userId);
    }

}

