package com.thelittlegym.mobile.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.thelittlegym.mobile.WebSecurityConfig;
import com.thelittlegym.mobile.common.OasisService;
import com.thelittlegym.mobile.common.WeixinService;
import com.thelittlegym.mobile.dao.ThemeDao;
import com.thelittlegym.mobile.entity.*;
import com.thelittlegym.mobile.enums.ResultEnum;
import com.thelittlegym.mobile.exception.MyException;
import com.thelittlegym.mobile.service.IUserService;
import com.thelittlegym.mobile.utils.ResultUtil;
import com.thelittlegym.mobile.utils.test.InTesting;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.alibaba.fastjson.JSON.parseObject;

/**
 * Created by 汁 on 2017/3/25.
 */
@Slf4j
@Controller
@RequestMapping("/index")
public class UserCtrl {
    @Autowired
    private IUserService userService;
    @Autowired
    private OasisService oasisService;
    @Autowired
    private WeixinService weixinService;
    @Value("${filePath}")
    private String filePath;
    @Autowired
    private ThemeDao themeDao;


    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(HttpServletRequest request,@SessionAttribute(WebSecurityConfig.SESSION_KEY) User user, Model model) throws Exception {
        try {
            HttpSession session = request.getSession();
            Object show = session.getAttribute("show");

            InTesting it = new InTesting();
            boolean ittest = it.isTrue(request);
            Object adminSession = session.getAttribute("admin");
            String linkId = (String)session.getAttribute("linkId");
            //log.info(linkId);
            //log.info("index1");
            if (show == null && !ittest && null == adminSession) {
                //log.info("index2");
                if (null != linkId && "1225".equals(linkId)) {
                    session.setAttribute("show", System.currentTimeMillis() / 1000);
                    return "redirect:/index";
                } else {
                    return "redirect:/noaccess.html";
                }
            }
            //log.info("index3");
            session.setAttribute("show", System.currentTimeMillis() / 1000);

            Map<String, String> weixinMap = new HashMap<String, String>();
            if (!ittest) {
                weixinMap = weixinService.getSignature(request.getRequestURL().toString());
            }


            //获孩子所有信息
            List<Child> listChild = new ArrayList<Child>();
            Integer idFamily = user.getIdFamily();
            String sqlUser = "declare @rest float=0,@dtend varchar(10)='',@gym varchar(50)='';select @gym =max(case when xh=1 then gym end),@rest =sum(kss),@dtend =convert(varchar(10),max(dtend),120) from(select top 6 row_number() over(order by bmksb.id desc)xh,case when ht.crmzdy_81733324<getdate() then 0 else bmksb.crmzdy_81739422 end kss,ht.crmzdy_81733324 dtend,crmzdy_81620171 gym from crm_zdytable_238592_25111_238592_view zx join crm_zdytable_238592_25115_238592_view bmksb on zx.crmzdy_81611091_id= idFamily and bmksb.crmzdy_81756836_id=zx.id  and bmksb.crmzdy_81733119='销售' and bmksb.crmzdy_81739422/*rest*/>0 join crm_zdytable_238592_23796_238592_view ht on ht.id =bmksb.crmzdy_81486464_id and datediff(d,getdate(),crmzdy_81733324/*dtDaoQi*/)>=0)bmksb;select hz.id  idhz,hz.crm_name name,hz.crmzdy_81497217 age,replace(isnull(hz.crmzdy_82017585,''),'''','\\\"')ranking,isnull(@rest,0) rest,isnull(@dtend,'无') dtend,@gym gym from crm_zdytable_238592_23893_238592_view hz where crmzdy_80653840_id=idFamily order by hz.id desc";
            sqlUser = sqlUser.replace("idFamily",idFamily.toString());
            //log.info(sqlUser);
            JSONArray childArray = oasisService.getResultJson(sqlUser);
            String str = childArray.toJSONString().replace("\\", "").replace("}\"", "}").replace("ranking\":\"\"", "ranking\":{}");
            str = "[" + str.replace(":\"{", ":{").replace("[", "").replace("]", "") + "]";
            List<Child> children = JSON.parseArray(str, Child.class);
           //log.info(children.toString());
            List<Gym> listGym = new ArrayList<Gym>();
            GymSelected gymSelected = new GymSelected();

            if (children != null) {
                Gym gym = new Gym();
                String beginDate;
                String endDate;
                String gymId;
                String gymName;
                beginDate = getInitDate().get(0);
                endDate = getInitDate().get(1);

                //中心列表及所选中心
                if (null == session.getAttribute("listGym")) {
                    String sqlGym = "select crmzdy_81620171 gymName,crmzdy_81620171_id gymId from crm_zdytable_238592_25111_238592_view where crmzdy_81611091_id =" + idFamily + " order by id desc";
                    JSONArray gymArray = oasisService.getResultJson(sqlGym);
                    listGym = JSONObject.parseArray(gymArray.toString(), Gym.class);
                } else {
                    listGym = (List<Gym>) session.getAttribute("listGym");
                }

                if (session.getAttribute("gymSelected") == null) {
                    gymId = listGym.get(0).getGymId();
                    gymName = listGym.get(0).getGymName();
                    gym.setGymId(gymId);
                    gym.setGymName(gymName);
                    gymSelected.setGym(gym);
                    gymSelected.setBeginDate(beginDate);
                    gymSelected.setEndDate(endDate);
                } else {
                    gymSelected = (GymSelected) session.getAttribute("gymSelected");
                }


                //根据中心显示孩子课程及考勤
                List<GymClass> listGymClass = new ArrayList<GymClass>();
                gymId = gymSelected.getGym().getGymId();
                beginDate = gymSelected.getBeginDate();
                endDate = gymSelected.getEndDate();
                String sqlClass = "select bj.crmzdy_80620202_id idgym,idChild,case when rq.crm_name>convert(varchar(5),getdate(),120)+'10-01' then rq.crmzdy_80695562-1 else rq.crmzdy_80695562 end weekNum,rq.crm_name date,bj.crmzdy_80612384 time,bj.crmzdy_80612382 course,case when kq='未考勤' then '尚未开课' else kq  end kq from(select bmks.crmzdy_81618215_id idChild,crmzdy_81486481 kq,crmzdy_81486480_id idrq from " +
                        "crm_zdytable_238592_25118_238592_view bmks where crmzdy_81618569_id=idjt and bmks.crmzdy_81636525>='beginDate'/*dtbegin*/ and bmks.crmzdy_81636525<='endDate'/*dtend*/ and crmzdy_81619234='已报名' union all select crmzdy_80658051_id idChild,crmzdy_80652349,crmzdy_80652340_id from crm_zdytable_238592_23696_238592_view bk where crmzdy_80631324_id=idjt and bk.crmzdy_81761865>='beginDate'/*dtbegin*/ and bk.crmzdy_81761865<='endDate'/*dtend*/)ks join crm_zdytable_238592_23870_238592_view rq on ks.idrq=rq.id join crm_zdytable_238592_23583_238592_view bj on rq.crmzdy_80650267_id=bj.id and bj.crmzdy_80620202_id='idGym'/*idgym*/order by date desc";
                sqlClass = sqlClass.replace("idjt",idFamily.toString()).replace("beginDate",beginDate).replace("endDate",endDate).replace("idGym",gymId);
                JSONArray classArray = oasisService.getResultJson(sqlClass);

                if (classArray != null) {
                    listGymClass = JSONObject.parseArray(classArray.toString(), GymClass.class);
                }

                for (Child child : children) {
                      Integer idChild = child.getIdhz();
                      List<GymClass> childGymClass = new ArrayList<GymClass>();
                      for(GymClass gymClass: listGymClass){
                          if(idChild.equals(gymClass.getIdChild())) {
                              childGymClass.add(gymClass);
                          }
                      }
                      child.setGymClasses(childGymClass);
                      listChild.add(child);
                }

            }
            //新人注册优惠活动
            //Boolean in3000 = userService.isNum(user.getTel(), 3000);
            //model.addAttribute("in3000", in3000);
            session.setAttribute("gymSelected", gymSelected);
            session.setAttribute("listGym", listGym);
            //log.info(gymSelected.toString());
            model.addAttribute("listChild", listChild);
            model.addAttribute("weixinMap", weixinMap);
        }catch (Exception e){
            log.error("/index错误信息：{}",e);
        }
        return "/member/index";
    }

    @GetMapping(value = "/topic")
    @ResponseBody
    public Result topic(String course,Integer weekNum) throws Exception {
        //log.info("{}-{}",course,weekNum);
        Theme theme = themeDao.findFirstByCourseAndWeekNumAndIsShow(course,weekNum,true);
        if(theme!=null){
            return ResultUtil.success(ResultEnum.SUCCESS,theme.getVideoSrc());
        }else{
            return ResultUtil.error();
        }

    }

    @RequestMapping(value = "/share", method = RequestMethod.GET)
    public String share(HttpServletRequest request, Model model) throws Exception {
        Map<String, String> returnMap = new HashMap<String, String>();
        String tian = request.getParameter("tian");
        String mins = request.getParameter("mins");
        String ranking = request.getParameter("ranking");
        String outpass = request.getParameter("outpass");
        String times_per_week = request.getParameter("times_per_week");
        String name = request.getParameter("name");
        String avatar = request.getParameter("avatar");

        returnMap.put("tian", tian);
        returnMap.put("mins", mins);
        returnMap.put("ranking", ranking);
        returnMap.put("outpass", outpass);
        returnMap.put("times_per_week", times_per_week);
        returnMap.put("name", name);
        returnMap.put("avatar", avatar);
//        for (Map.Entry entry : returnMap.entrySet()) {
//            System.out.println(entry.getKey() + ", " + entry.getValue());
//        }
        model.addAttribute("shareMap", returnMap);
        return "/member/share";
    }


    @RequestMapping(value = "/myinfo", method = RequestMethod.GET)
    public String myinfo(@SessionAttribute(WebSecurityConfig.SESSION_KEY) User user,String gymName,String idhz, String name,String age,Model model) throws Exception {
        try {

            //我的信息
            Integer idFamily = user.getIdFamily();
            String tel = user.getTel();
            String  sqlMyInfo = " select top 6 convert(varchar(10),ht.crmzdy_80646021,111) 报名日期,ht.crmzdy_80646031  报名课时数,ht.crmzdy_81636090 合同金额,convert(varchar(10),crmzdy_81733324,111)有效期,case when ht.crmzdy_81733324<getdate() then 0 else bmksb.crmzdy_81739422 end 剩余课时数,bmksb.crmzdy_81768505 活动扣课数,bmksb.crmzdy_81739425 累计请假数,isnull(bjap.kc,'暂未排课') 课程,ht.crmzdy_81733120 赠课,zx.crmzdy_81802626 积分,isnull(zx.crmzdy_82034325,0) pointed from crm_zdytable_238592_25111_238592_view zx join crm_zdytable_238592_25115_238592_view bmksb on zx.crmzdy_81611091_id="+ idFamily+" and bmksb.crmzdy_81756836_id=zx.id  join crm_zdytable_238592_23796_238592_view" +
                    " ht on ht.id  =bmksb.crmzdy_81486464_id  outer apply(select top 1 bj.crmzdy_80612382 kc from crm_zdytable_238592_25117_238592_view bjap join crm_zdytable_238592_23583_238592_view bj on bj.id  =bjap.crmzdy_81486476_id where ht.id  =bjap.crmzdy_81598938_id)bjap where bmksb.crmzdy_81733119='销售'  and bmksb.crmzdy_81739422/*rest*/>0 and datediff(d,getdate(),ht.crmzdy_81733324/*dtDaoQi*/)>=0";
            JSONArray contractArr = oasisService.getResultJson(sqlMyInfo);

            JSONObject childObj = new JSONObject();
            childObj.put("idhz", idhz);
            childObj.put("name", name);
            childObj.put("age", age);
            childObj.put("age", age);
            childObj.put("gymSelected", gymName);

            model.addAttribute("listContract", contractArr);
            model.addAttribute("child", childObj);
        }catch(Exception e){
            log.error("错误信息：{}",e);
        }

        return "/member/myinfo";
    }



    //考勤查询
    @RequestMapping(value = "/attend", method = RequestMethod.GET)
    @ResponseBody
    public Result getAttend(HttpServletRequest request,@SessionAttribute(WebSecurityConfig.SESSION_KEY) User user,String idGym, String nameGym, Integer idChild, String beginDate, String endDate, Integer child_index) {
        HttpSession session = request.getSession();
        JSONObject jsonObject = new JSONObject();
        JSONArray classArray = new JSONArray();
        List<GymSelected> listGymSelected = new ArrayList<GymSelected>();
        if (user == null) {
            throw new MyException(ResultEnum.FAILURE);
        }
        //session更新GymSelected
        Object listGymSelectedSession = session.getAttribute("listGymSelectedSession");
        if (listGymSelectedSession != null) {
            listGymSelected = (List<GymSelected>) listGymSelectedSession;
            GymSelected gymSelected = new GymSelected();
            Gym gym = new Gym();
            gym.setGymId(idGym);
            gym.setGymName(nameGym);
            gymSelected.setGym(gym);
            gymSelected.setBeginDate(beginDate);
            gymSelected.setEndDate(endDate);
            listGymSelected.set(child_index, gymSelected);
        }
        session.setAttribute("gymSelected", listGymSelectedSession);

        String sqlClass = "select bj.crmzdy_80620202_id idgym,case when rq.crm_name>convert(varchar(5),getdate(),120)+'10-01' then rq.crmzdy_80695562-1 else rq.crmzdy_80695562 end weekNum,rq.crm_name date,bj.crmzdy_80612384 time,bj.crmzdy_80612382 course,case when kq='未考勤' then '尚未开课' else kq  end kq from(select crmzdy_81486481 kq,crmzdy_81486480_id idrq " +
                "from crm_zdytable_238592_25118_238592_view bmks where bmks.crmzdy_81618215_id=" + idChild + " /*idhz*/ and bmks.crmzdy_81636525>='" + beginDate + "'/*dtbegin*/ and bmks.crmzdy_81636525<='" + endDate + "'/*dtend*/ and crmzdy_81619234='已报名' union all select crmzdy_80652349,crmzdy_80652340_id from crm_zdytable_238592_23696_238592_view bk where crmzdy_80658051_id=" + idChild + "  and bk.crmzdy_81761865>='" + beginDate + "'/*dtbegin*/ and bk.crmzdy_81761865<='" + endDate + "'/*dtend*/)ks join crm_zdytable_238592_23870_238592_view rq on ks.idrq=rq.id join crm_zdytable_238592_23583_238592_view bj on rq.crmzdy_80650267_id=bj.id and bj.crmzdy_80620202_id=" + idGym + "/*idgym*/order by date desc";
        classArray = oasisService.getResultJson(sqlClass);

        if (classArray != null) {
            return ResultUtil.success(ResultEnum.SUCCESS.getMessage(),classArray);
        }
        return ResultUtil.error();

    }

    //头像上传
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public Result getAttend(HttpServletRequest request,@SessionAttribute(WebSecurityConfig.SESSION_KEY) User user, MultipartFile file) {
        String tel = user.getTel();
        try {
            // 获取图片原始文件名
            String originalFilename = file.getOriginalFilename();

            // 文件名使用当前时间
            // String name = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
            //String rootPath = request.getSession().getServletContext().getRealPath("");
            String rootPath = filePath;
            String name = "avatar";
            String subfolder =  "/upload/";
            String relativePath = subfolder + name + "/"+ tel + "/";
            String realPath =  rootPath +  relativePath;

            // 获取上传图片的扩展名(jpg/png/...)
            String extension = FilenameUtils.getExtension(originalFilename);
            // 图片上传的绝对路径
            String originalUrl =  realPath  + name + "Original" + "." + extension;

            //缩略图PATH
            String urlNoExtension = realPath  + name;
            String urlHttp = "/files" + relativePath  + name + "." + "jpg";
            File dir = new File(realPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // 上传图片
            file.transferTo(new File(originalUrl));
            //压缩
            Thumbnails.of(originalUrl).size(200, 200).outputFormat("jpg").toFile(urlNoExtension);
            user.setHead_src(urlHttp);
            userService.updateUser(user);
            HttpSession session = request.getSession();
            session.setAttribute("user",user);

            return ResultUtil.success(ResultEnum.UPLOAD_SUCCESS.getMessage());
        } catch (Exception e) {
            throw new MyException(ResultEnum.UPLOAD_TRY_LATER);
        }
    }

    /*
    ranking转化jsonobject
     */
    public JSONObject rankUtil(String ranking, String text) throws Exception {
        JSONObject returnObj = null;
        if (ranking != null && !ranking.equals("")) {
            JSONObject jsonObject = parseObject(ranking);
            JSONArray jsonArray = jsonObject.getJSONArray(text);
            returnObj = jsonArray.getJSONObject(0);
        }
        return returnObj;
    }

    //获得查询开始结束日期
    public static ArrayList<String> getInitDate() {
        ArrayList<String> list = new ArrayList<String>();
        Calendar cal = Calendar.getInstance();
        String beginDate = "";
        String endDate = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.add(Calendar.WEEK_OF_MONTH, -1);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        beginDate = sdf.format(cal.getTime());

        //下下周
        cal.add(Calendar.WEEK_OF_MONTH, 3);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        endDate = sdf.format(cal.getTime());
        list.add(beginDate);
        list.add(endDate);
        return list;
    }
}
