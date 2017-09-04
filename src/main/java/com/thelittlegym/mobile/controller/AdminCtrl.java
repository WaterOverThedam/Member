package com.thelittlegym.mobile.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.thelittlegym.mobile.dao.ActivityDao;
import com.thelittlegym.mobile.dao.ThemeDao;
import com.thelittlegym.mobile.entity.*;
import com.thelittlegym.mobile.enums.ResultEnum;
import com.thelittlegym.mobile.enums.menuEnum;
import com.thelittlegym.mobile.service.*;
import com.thelittlegym.mobile.utils.saveUtil;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
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
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

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

    @RequestMapping(value="/login",method = RequestMethod.GET)
    public String adminToLogin(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        Object sessionObj = session.getAttribute("admin");
        Admin admin = new Admin();
        if (sessionObj != null){
            return "/admin";
        }else{
            return "/admin/login";
        }
    }

    @RequestMapping(value="/valLogin",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> adminLogin(HttpServletRequest request, String username, String password) throws Exception {
        Map<String,Object> returnMap = new HashMap<String,Object>();
        try {
            Map<String ,Object> map =  adminService.login( username, password);
            Object object = map.get("value");
            if (object != null) {
                Admin admin = (Admin) object;
                HttpSession session = request.getSession();
                session.setAttribute("admin", admin);
            }
            returnMap.put("value", object);
            returnMap.put("message",ResultEnum.LOGIN_SUCCESS);
        }catch (Exception e){
            returnMap.put("result", ResultEnum.LOGIN_WRONG_PWD);
            log.error(e.getMessage());
        }

        return returnMap;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String adminIndex (HttpServletRequest request, @RequestParam(value = "pageNow", defaultValue = "1") Integer pageNow,
                                    @RequestParam(value = "size", defaultValue = "10") Integer size,
                                    Model model) throws Exception {
   //ToDo admin权限
        HttpSession session = request.getSession();
        Object obj = session.getAttribute("menu");
        if (obj==null) {
            model.addAttribute("menu", menuEnum.ACTIVITY);
            session.setAttribute("menu", menuEnum.ACTIVITY);
        }else{
            model.addAttribute("menu",obj);
        }

        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Pageable pageable = new PageRequest(pageNow, size, sort);
        Page<Activity> pages = activityDao.findAll(pageable);
        model.addAttribute("page", pages);
        return "/admin/index";
    }

    @RequestMapping(value = "/{menu}.html", method = RequestMethod.GET)
    public String adminIndex(@PathVariable("menu") String menu, HttpServletRequest request ,
                             @RequestParam(value = "pageNow", defaultValue = "1") Integer pageNow,
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             String keyword,Model model) throws Exception {
        HttpSession session = request.getSession();
        Object sessionObj = session.getAttribute("admin");
        if (sessionObj == null){
            return "/admin/login";
        }


        model.addAttribute("menu", menuEnum.getMenu(menu));
        session.setAttribute("menu", menuEnum.getMenu(menu));

        Sort sort = new Sort(Sort.Direction.DESC, "creatTime");
        Pageable pageable = new PageRequest(pageNow, size, sort);

        switch (menu){
            case "activity":
                Page<Activity> activityPages = activityDao.findAll(pageable);
                model.addAttribute("pages", activityPages);
                break;
            case "theme":
                Page<Theme> themePages = themeDao.findAll(pageable);
                model.addAttribute("page", themePages);
                break;
        }

        return "/admin/index";
    }

    @RequestMapping(value = "/add/{menu}", method = RequestMethod.GET)
    public String activityToAdd(@PathVariable("menu") String menu, HttpServletRequest request, Model model) throws Exception {
        HttpSession session = request.getSession();
        Object sessionObj = session.getAttribute("admin");
        if (sessionObj == null){
            return "/admin/login";
        }
        //System.out.println(menu);
        return "/admin/" + menu + "Add";
        //return  "/admin/activityAdd";
    }

    @RequestMapping(value = "/addTo/{menu}")
    @ResponseBody
    public HashMap Add(HttpServletRequest request, MultipartFile videoFile, @PathVariable String menu) throws Exception {
        String name=request.getParameter("name");
        String type=request.getParameter("type");
        String weekNum=request.getParameter("weekNum");

        //System.out.println(menu);
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        try {
            // 获取图片原始文件名
            String originalFilename = videoFile.getOriginalFilename();
            //System.out.println(originalFilename);
            // 文件名使用当前时间
//            String name = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
            // 获取上传图片的扩展名(jpg/png/...)
            String extension = FilenameUtils.getExtension(originalFilename);

            // 图片上传的相对路径（因为相对路径放到页面上就可以显示图片）
            String path = "/upload/video/" + type + "/" +type+ "_" +weekNum + "." + extension.toUpperCase();

            // 图片上传的绝对路径
            String url = request.getSession().getServletContext().getRealPath("") + path;

            String urlHttp = "/upload/video/" + type + "/" + weekNum + "." + "MP4";
            File dir = new File(url);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // 上传图片
            videoFile.transferTo(new File(url));

            returnMap.put("success", true);
            returnMap.put("path", path);

            saveUtil.save(menu,request,returnMap);
        } catch (Exception e) {
            e.printStackTrace();
            returnMap.put("success", false);
            returnMap.put("message", "请重新登录后再试");
        }

        return returnMap;
    }

    @RequestMapping(value = "/edit/{menu}", method = RequestMethod.GET)
    public String activityToEdit(@PathVariable("menu") String menu, HttpServletRequest request, Model model, Integer id) throws Exception {
        HttpSession session = request.getSession();
        Object sessionObj = session.getAttribute("admin");
        if (sessionObj == null){
            return "/admin/login";
        }
        Activity activity = activityDao.findOne(id);
        model.addAttribute("activity", activity);
        return "/admin/activityEdit";
    }

    @RequestMapping(value = "/edit/{menu}", method = RequestMethod.POST)
    public String activityEdit(@PathVariable("menu") String menu, HttpServletRequest request, MultipartFile banner, String name, String detail, String  beginDate, String endDate, String type, String chargeType, String crowd, String strength) throws Exception {
        HttpSession session = request.getSession();
        Object sessionObj = session.getAttribute("admin");
        if (sessionObj == null){
            return "/admin/login";
        }
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
                if (!banner.isEmpty()){
                    delFile(request.getSession().getServletContext().getRealPath("") + activity.getBannerSrc());
                    activity.setBannerSrc(uploadFile(request, banner, "/upload/admin/activity/banner/"));
                }
                activityDao.save(activity);
            }
        }
        return "redirect:/admin";
    }

    @RequestMapping(value = "/del/{menu}", method = RequestMethod.GET)
    public String activityDel(@PathVariable("menu") String menu, HttpServletRequest request, Integer id) throws Exception {
        HttpSession session = request.getSession();
        Object sessionObj = session.getAttribute("admin");
        if (sessionObj == null){
            return "/admin/login";
        }
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        Activity activity  = new Activity();
        if(null != id && !"".equals(id)){
            activity = activityDao.findOne(id);
            if ( null != activity ){
                activity.setIsDelete(true);
                activityDao.save(activity);
            }
        }
        activityDao.save(activity);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/simulation", method = RequestMethod.GET)
    public String simulation(HttpServletRequest request, Model model) throws Exception {
        HttpSession session = request.getSession();
        Object sessionObj = session.getAttribute("admin");
        if (sessionObj == null) {
            return "/admin/login";
        }
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
    public Map<String,Object> member(HttpServletRequest request, String tel)  {
        HttpSession session = request.getSession();
        Map<String,Object> returnMap = new HashMap<String,Object>();
        Map<String,Object> map = null;
        try {
            map = loginService.login(tel);
            Object object = map.get("value");
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
                returnMap.put("success",true);
                returnMap.put("message","登录成功");
            }
        } catch (Exception e) {
            returnMap.put("success",false);
            returnMap.put("message","登录异常，请重试");
        }
        //获取user实体
        Object object = map.get("value");
        if (object != null) {
            User user = (User) object;
            Object objSession = session.getAttribute("user");

            session.setAttribute("user", user);
            returnMap.put("result",ResultEnum.LOGIN_SUCCESS);
            returnMap.put("message","登录成功");
            return returnMap;
        }else {
            returnMap.put("result",ResultEnum.LOGIN_USER_NO_EXIST);
            return returnMap;
        }
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


    @RequestMapping(value="/feedback",method = RequestMethod.GET)
    public String feedback(HttpServletRequest request,@RequestParam(value = "pageNow", defaultValue = "1") Integer pageNow,
                           @RequestParam(value = "size", defaultValue = "10") Integer size,
                           Model model) throws Exception {
        HttpSession session = request.getSession();
        Object sessionObj = session.getAttribute("admin");
        if (sessionObj == null){
            return "/admin/login";
        }

        //TODO 切换反馈类型等操作
//        Object typeObj = session.getAttribute("selectedFeedback");
        String type = "0";
//        type = request.getParameter("type");
//        if (null == type || "".equals(type)){
//            if(null == typeObj || "".equals(typeObj)){
//                type = "0";
//            }else{
//                type = typeObj.toString();
//            }
//        }
//        session.setAttribute("selectedFeedback",type);
//        session.setAttribute("selectedFeedback",type);
        Sort sort = new Sort(Sort.Direction.DESC, "creatTime");
        Pageable pageable = new PageRequest(pageNow, size, sort);
        Page<Activity> feedbackPages = activityDao.findAll(pageable);
        Long handledCount = feedbackService.handledCount(true);
        model.addAttribute("page", feedbackPages);
        model.addAttribute("handledCount",handledCount);
        return "/admin/feedback";
    }

    @RequestMapping(value="/feedbackView",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject feedbackView(HttpServletRequest request, String id) throws Exception {
        Integer fid = -1;
        if (null != id && id.matches("[0-9]+")){
            fid = Integer.parseInt(id);
        }
        Feedback feedback = feedbackService.findOne(fid);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        String name = "".equals(feedback.getName()) ? "匿名":feedback.getName();
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(feedback);
        jsonObject.put("createTime",sdf.format(feedback.getCreateTime()));
        jsonObject.put("name",name);
        return jsonObject;
    }

    @RequestMapping(value="/sign",method = RequestMethod.POST)
    @ResponseBody
    public Boolean sign(HttpServletRequest request, String id) throws Exception {
        Integer fid = -1;
        JSONObject jsonObject = new JSONObject();
        if (null != id && id.matches("[0-9]+")){
            fid = Integer.parseInt(id);
            feedbackService.handle(fid);
            return true;
        }else{
            return false;
        }
    }

    @RequestMapping(value="/exit",method = RequestMethod.GET)
    public String exit(HttpServletRequest request, String id) throws Exception {
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/admin";
    }

    @RequestMapping(value="/actUser",method = RequestMethod.GET)
    public String actUser(HttpServletRequest request,@RequestParam(value = "pageNow", defaultValue = "1") Integer pageNow,
                          @RequestParam(value = "size", defaultValue = "20") Integer size,
                          Model model) throws Exception {
        HttpSession session = request.getSession();
        Object sessionObj = session.getAttribute("admin");
        if (sessionObj == null){
            return "/admin/login";
        }

        Sort sort = new Sort(Sort.Direction.DESC,"createTime");
        Pageable pageable =new PageRequest(pageNow,size,sort);
        Page<User> userPage = userService.getUserPageList(pageable);
        model.addAttribute("page",userPage);
        return "/admin/actUser";
    }
    /*
        工具类
     */
    //删除指定文件
    public boolean delFile(String filePath){
        boolean flag = false;
        File file = new File(filePath);
        if (file.exists()){
            file.delete();
            flag = true;
        }
        return flag;
    }



    public String uploadFile(HttpServletRequest request, MultipartFile file, String headPath) throws IOException {
        // 获取图片原始文件名
        String originalFilename = file.getOriginalFilename();

//             文件名使用当前时间
        String fileName = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        // 获取上传图片的扩展名(jpg/png/...)
        String extension = FilenameUtils.getExtension(originalFilename);

        // 图片上传的相对路径（因为相对路径放到页面上就可以显示图片）
        String path = headPath + fileName +"." + extension.toUpperCase();
//        String originalPath = headPath + fileName + "Original" + "." + extension;
        // 图片上传的绝对路径
        String url = request.getSession().getServletContext().getRealPath("") + path;
//        String originalUrl = request.getSession().getServletContext().getRealPath("") + originalPath;
        File dir = new File(url);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 上传图片
        file.transferTo(new File(url));

        //压缩
        Thumbnails.of(url).size(960, 700).toFile(url);
        return path;
    }
}
