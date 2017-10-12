package com.thelittlegym.mobile.controller;


import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.pagehelper.PageHelper;
import com.thelittlegym.mobile.config.MyConfig;
import com.thelittlegym.mobile.entity.*;
import com.thelittlegym.mobile.mapper.AdminMapper;
import com.thelittlegym.mobile.mapper.PageLogMapper;
import com.thelittlegym.mobile.mapper.UserMapper;
import com.thelittlegym.mobile.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import org.jsoup.nodes.Document;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.jws.soap.SOAPBinding;
import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by Tony on 2017/8/31.
 */

@Controller
@Slf4j
@RequestMapping("/test")
public class testCtrl  {
    @Autowired
    private PageLogMapper pageLogMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private IUserService userService;
    @Autowired
    private MyConfig myConfig;
    @GetMapping(value = "/say")
    private String say(ModelMap map){
        ClassPathResource res = new ClassPathResource("static/common_admin.html");
        //System.out.println(this.getClass().getResource("/").getPath());

        try {
            InputStream input = res.getInputStream();
            Document   doc = Jsoup.parse(input,"UTF-8","");
            log.error(doc.toString());
            List<User> users = userService.getUserList();
            map.addAttribute("head", doc.select("meta,link").toString());
            map.addAttribute("menu", doc.select(".item").toString());
            map.addAttribute("users",users);
            return "test";
        } catch (IOException e) {
            e.printStackTrace();
            return  null;
        }

    }

    @GetMapping(value = "/tell")
    @ResponseBody
    private  List <PageLogGroup>  tell(){
        PageHelper.startPage(3,1);
        List <PageLogGroup> p =pageLogMapper.getPageLogStat("pageURL,requestType");

        return p;
    }
    @GetMapping(value = "/user")
    @ResponseBody
    private  List <User>  you(){
        PageHelper.startPage(3,2);
        List<User> u = userMapper.getAll();
        return u;
    }

    @GetMapping(value = "/admin")
    @ResponseBody
    private Admin who(){
        Admin admin= adminMapper.getAdminByUsername("market");
        return admin;
    }
    @GetMapping(value = "/conf")
    @ResponseBody
    private String config(){

        return myConfig.toString();
    }

    @GetMapping(value = "/getPar")
    @ResponseBody
    public User getPar(@RequestParam(value = "userId",defaultValue="",required = false) Long userId, @RequestParam(value = "names",defaultValue ="", required = false)List names) throws Exception {

        return userMapper.getParticipatorsTobe(userId,names);


    }
}
