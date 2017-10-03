package com.thelittlegym.mobile.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.thelittlegym.mobile.Convertor.DateConvert;
import com.thelittlegym.mobile.common.JsonService;
import com.thelittlegym.mobile.dao.ActivityDao;
import com.thelittlegym.mobile.dao.PageLogDao;
import com.thelittlegym.mobile.dao.ThemeDao;
import com.thelittlegym.mobile.entity.*;
import com.thelittlegym.mobile.enums.ResultEnum;
import com.thelittlegym.mobile.exception.MyException;
import com.thelittlegym.mobile.mapper.AdminMapper;
import com.thelittlegym.mobile.mapper.PageLogMapper;
import com.thelittlegym.mobile.service.IAdminService;
import com.thelittlegym.mobile.service.IFeedbackService;
import com.thelittlegym.mobile.service.ILoginService;
import com.thelittlegym.mobile.service.IUserService;
import com.thelittlegym.mobile.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by hibernate on 2017/5/15.
 */
@Controller
@Slf4j
@RequestMapping("/admin")
public class AdminCtrl {
    @Autowired
    private ActivityDao activityDao;
    @Autowired
    private ThemeDao themeDao;
    @Autowired
    private IAdminService adminService;
    @Autowired
    private ILoginService loginService;
    @Autowired
    private IFeedbackService feedbackService;
    @Autowired
    private IUserService userService;
    @Autowired
    private PageLogDao pageLogDao;
    @Autowired
    private PageLogMapper pageLogMapper;

    @Autowired
    private JsonService jsonService;
    @GetMapping(value="/login")
    public String adminToLogin(HttpServletRequest request) throws Exception {

            return "/admin/login";

    }

    @PostMapping(value="/login")
    @ResponseBody
    public Result adminLogin(HttpServletRequest request, String username,String password) throws Exception {
        Result result = adminService.login(username,password);
        HttpSession session = request.getSession();
        session.setAttribute("admin", result.getData());
        return result;
    }

    @RequestMapping(value = {"","/index","/activity"}, method = RequestMethod.GET)
    public String adminIndex (HttpServletRequest request, @RequestParam(value = "pageNow", defaultValue = "1") Integer pageNow,
                              @RequestParam(value = "size", defaultValue = "10") Integer size,
                              @RequestParam(value = "city", defaultValue = "") Integer city,
                              Model model) throws Exception {
        HttpSession session = request.getSession();
        session.setAttribute("menuId","activity");

        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Pageable pageable = new PageRequest(pageNow-1, size, sort);
        Page<Activity> pages = activityDao.findAll(pageable);
        model.addAttribute("page", pages);
        return "/admin/index";
    }

    @GetMapping(value = "/theme")
    public String adminIndex(HttpServletRequest request ,
                             @RequestParam(value = "pageNow", defaultValue = "1") Integer pageNow,
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             @RequestParam(value = "kw", defaultValue = "") String keyword,
                             Model model) throws Exception {

        HttpSession session = request.getSession();
        //Json同步数据
        //        List<Theme> themes =jsonService.getThemeData();
        //        if(themes!=null){
        //            themeDao.save(themes);
        //        }
        session.setAttribute("menuId","theme");

        keyword = "%" + keyword + "%";
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Pageable pageable = new PageRequest(pageNow-1, size, sort);
        Theme theme = new Theme();
        Page<Theme> themePages = themeDao.findAllBySearchLike(keyword,pageable);
        log.info(String.valueOf(themePages.getTotalElements()));

        model.addAttribute("page", themePages);

       return  "/admin/index";
    }



    @PostMapping(value = "/theme")
    @ResponseBody
    public Result adminIndex(HttpServletRequest request) throws Exception {

        Integer id=Integer.parseInt(request.getParameter("id"));
        Boolean isShow = Boolean.parseBoolean(request.getParameter("isShow"));
        Theme theme = themeDao.findOne(id);
        theme.setIsShow(isShow);
        Theme res = themeDao.save(theme);
        if(res!=null){
            return ResultUtil.success();
        }else{
            return ResultUtil.error();
        }
    }


    @GetMapping(value = "/themeDel")
    @ResponseBody
    public Result themeDel(Integer id) throws Exception {
        themeDao.delete(id);
        return ResultUtil.success();

    }

    @GetMapping(value = "/activityAdd")
    public String activityToAdd( @RequestParam(value = "id",required = false) Integer id,Model model) throws Exception {
        Activity activity=null;
        if (id != null){
             activity = activityDao.findOne(id);
        }
        model.addAttribute("activity",activity);
        return "/admin/activityAdd";
    }

    @RequestMapping(value = "/activityHandle")
    @ResponseBody
    public HashMap Add(HttpServletRequest request, MultipartFile file) throws Exception {
        String gym = request.getParameter("gym");
        Activity activity= new Activity();

        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        try {
            // 获取图片原始文件名
            String originalFilename = file.getOriginalFilename();
            //System.out.println(originalFilename);
            // 文件名使用当前时间
            String name = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
            // 获取上传图片的扩展名(jpg/png/...)
            String extension = FilenameUtils.getExtension(originalFilename);

            // 图片上传的相对路径（因为相对路径放到页面上就可以显示图片）
            String path = "/upload/activity/" + gym + "/"  +name + "." + extension.toUpperCase();

            // 图片上传的绝对路径
            String url = request.getSession().getServletContext().getRealPath("") + path;

            String urlHttp = "/upload/activity/" + gym + "/" + name + "." + "jpg";
            File dir = new File(url);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // 上传图片
            file.transferTo(new File(url));

            returnMap.put("success", true);
            returnMap.put("path", path);
            activityDao.save(activity);

        } catch (Exception e) {
            e.printStackTrace();
            returnMap.put("success", false);
            returnMap.put("message", "请重新登录后再试");
        }

        return returnMap;
    }



    @PostMapping(value = "/themeAdd")
    @ResponseBody
    public Result  themeAdd(HttpServletRequest request) throws Exception {

        String course =request.getParameter("course");
        log.info(course);
        String course_id=request.getParameter("course_id");
        String weekNum =request.getParameter("weekNum");
        String videoSrc = "/files/video/"+course_id+"_"+ weekNum+".mp4";
        String dtBegin=request.getParameter("dtBegin");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse( dtBegin );
        Theme theme = new Theme();
        theme.setIsShow(true);
        theme.setCourse(course);
        theme.setBeginDate(date);
        theme.setWeekNum(Integer.parseInt(weekNum));
        theme.setVideoSrc(videoSrc);
        theme.setCreateTime(new Date());
        theme.setSearch(theme.toString());
        themeDao.save(theme);
        return ResultUtil.success();
    }




    @RequestMapping(value = "/edit/{menu}", method = RequestMethod.GET)
    public String activityToEdit(@PathVariable("menu") String menu, HttpServletRequest request, Model model, Integer id) throws Exception {

        Activity activity = activityDao.findOne(id);
        model.addAttribute("activity", activity);
        return "/admin/activityEdit";
    }

    @RequestMapping(value = "/edit/{menu}", method = RequestMethod.POST)
    public String activityEdit(@PathVariable("menu") String menu, HttpServletRequest request, MultipartFile banner, String name, String detail, String  beginDate, String endDate, String type, String chargeType, String crowd, String strength) throws Exception {

        Integer id = Integer.parseInt(request.getParameter("id"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-DD");
        Activity activity = new Activity();
        if(null != id && !"".equals(id)){
            activity = activityDao.findOne(id);
            if ( null != activity ){
                activity.setName(name);
                activity.setDetail(detail);
                activity.setBeginDate(sdf.parse(beginDate));
                activity.setEndDate(sdf.parse(endDate));
                activity.setType(type);
                activity.setChargeType(chargeType);
                activity.setCrowd(crowd);
                activity.setStrength(strength);
                activity.setCreateTime(new Date());
                activity.setGyms("上海环球中心");
                /*
                if (!banner.isEmpty()){
                    delFile(request.getSession().getServletContext().getRealPath("") + activity.getBannerSrc());
                    activity.setBannerSrc(uploadFile(request, banner, "/upload/admin/activity/banner/"));
                }
                */
                activityDao.save(activity);
            }
        }
        return "redirect:/admin";
    }


    @RequestMapping(value = "/simulation", method = RequestMethod.GET)
    public String simulation(HttpServletRequest request, Model model) throws Exception {

        String tel = request.getParameter("tel");
        Long totalMember = userService.getTotal();

        if (null != tel){
            model.addAttribute("tel",tel);
        }
        if(null != totalMember){
            model.addAttribute("totalMember",totalMember);
        }
        return "/admin/simulation";
    }

    @RequestMapping(value = "/member", method = RequestMethod.POST)
    @ResponseBody
    public Result member(HttpServletRequest request, String tel)  {
        HttpSession session = request.getSession();
        try {
            Result result = loginService.login(tel);
            Object object = result.getData();
            if (object != null) {
                User user = (User) object;
                session = request.getSession(true);
                Object objSession = session.getAttribute("user");
                //重复登录清空之前session所有attr
                if ( null != objSession ){
                    Enumeration<String> em = session.getAttributeNames();
                    while (em.hasMoreElements()) {
                        String removeAttr = em.nextElement();
                        if(!"admin".equals(removeAttr)){
                            session.removeAttribute(removeAttr);
                        }
                    }
                }

                session.setAttribute("user", user);
                return result;
            }
        } catch (Exception e) {
                throw e;
        }
             return ResultUtil.error();
    }

    @RequestMapping(value = "/activityView", method = RequestMethod.POST)
    @ResponseBody
    //TODO   很多判断，懒得写了
    public JSONArray activityView(HttpServletRequest request, Integer id) throws Exception {
        Activity activity  = new Activity();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.hh HH:mm:ss");
        activity = activityDao.findOne(id);
        sdf.format(activity.getBeginDate());
        JSONObject jsonObject = (JSONObject)JSON.toJSON(activity);
        jsonObject.put("beginDate",sdf.format(activity.getBeginDate()));
        jsonObject.put("endDate",sdf.format(activity.getEndDate()));
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(jsonObject);

        return jsonArray;
    }



    @GetMapping(value="/feedback")
    public String feedback(HttpServletRequest request,@RequestParam(value = "pageNow", defaultValue = "1",required = false) Integer pageNow,
                           @RequestParam(value = "size", defaultValue = "8",required = false) Integer size,
                           @RequestParam(value = "type", defaultValue = "0",required = false) Integer type,
                           Model model) throws Exception {

        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Pageable pageable = new PageRequest(pageNow-1, size, sort);
        Page<Feedback> feedbackPages = feedbackService.findAllByHandled(type,pageable);
        model.addAttribute("page", feedbackPages);
        return "/admin/feedback";
    }


    @PostMapping(value="/feedback")
    @ResponseBody
    public Result feedbackView(HttpServletRequest request, String id) throws Exception {
        Integer fid = -1;
        if (null != id && id.matches("[0-9]+")){
            fid = Integer.parseInt(id);
        }

        Feedback feedback = feedbackService.findOne(fid);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        String name = "".equals(feedback.getName()) ? "匿名":feedback.getName();

        return ResultUtil.success(feedback);
    }

    @RequestMapping(value="/sign",method = RequestMethod.POST)
    @ResponseBody
    public Result sign(HttpServletRequest request, String id) throws Exception {
        Integer fid = -1;
        JSONObject jsonObject = new JSONObject();
        if (null != id && id.matches("[0-9]+")){
            fid = Integer.parseInt(id);
            feedbackService.handle(fid);
            return ResultUtil.success();
        }else{
            return ResultUtil.error();
        }
    }

    @GetMapping(value="/pageLog")
    public String statView(HttpServletRequest request,@RequestParam(value = "pageNow", defaultValue = "1",required = false) Integer pageNow,
                           @RequestParam(value = "kw",required = false) String keyWord,@RequestParam(value = "size", defaultValue = "16",required = false) Integer size,
                           @RequestParam(value = "dtBegin",required = false ) String dtBegin,@RequestParam(value = "dtEnd", required = false) String dtEnd,
                           Model model) throws Exception {

        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Pageable pageable = new PageRequest(pageNow-1, size, sort);
        HttpSession session = request.getSession();
        session.setAttribute("menuId","pageLog");

        Page<PageLog> page;
        if(dtBegin!=null){
            page = pageLogDao.findAllBySearchIsLikeAndCreateTimeBetween('%'+keyWord+'%',DateConvert.convert(dtBegin),DateConvert.convert(dtEnd),pageable);
        }else if(keyWord != null && keyWord != ""){
            page = pageLogDao.findAllBySearchIsLike('%'+keyWord+'%',pageable);
        }else {
            page = pageLogDao.findAll(pageable);
        }
        model.addAttribute("page",page);
        return "/admin/index";
    }

    @GetMapping(value="/pageLogGroup")
    public String statGroupView(HttpServletRequest request,@RequestParam(value = "pageNow", defaultValue = "1",required = false) Integer pageNow,
                           @RequestParam(value = "gp",required = false ) String groups,@RequestParam(value = "pageSize", defaultValue = "10",required = false) Integer pageSize,
                           @RequestParam(value = "dtBegin",required = false ) String dtBegin,@RequestParam(value = "dtEnd",required = false) String dtEnd,
                           Model model) throws Exception {

        HttpSession session = request.getSession();
        session.setAttribute("menuId","pageLog");

        if (groups == null || groups == "") {
            groups ="pageURL,requestType";
        }

        PageHelper.startPage(pageNow,pageSize);
        List<PageLogGroup> page;
        if(dtBegin!=null){
            page = pageLogMapper.getPageLogStatByCreatTime(groups,dtBegin,dtEnd);
        }else {
            page = pageLogMapper.getPageLogStat(groups);
        }

        model.addAttribute("page",page);
        model.addAttribute("groups",groups);


        return "/admin/index";

    }
    @PostMapping(value="/pageLog")
    public String statData(HttpServletRequest request, String id) throws Exception {

        HttpSession session = request.getSession();
        session.invalidate();
        return "/admin/index";
    }

    @RequestMapping(value="/exit",method = RequestMethod.GET)
    public String exit(HttpServletRequest request, String id) throws Exception {
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/admin";
    }

    @RequestMapping(value="/enrollist",method = RequestMethod.GET)
    public String actUser(HttpServletRequest request,@RequestParam(value = "pageNow", defaultValue = "1") Integer pageNow,
                          @RequestParam(value = "size", defaultValue = "20") Integer size,
                          Model model) throws Exception {

        Sort sort = new Sort(Sort.Direction.DESC,"createTime");
        Pageable pageable =new PageRequest(pageNow,size,sort);
        Page<User> userPage = userService.getUserPageList(pageable);
        model.addAttribute("page",userPage);
        return "/admin/actUser";
    }



}
