package com.thelittlegym.mobile.controller;


import com.alibaba.fastjson.JSON;
import com.thelittlegym.mobile.entity.Msg;
import com.thelittlegym.mobile.entity.User;
import com.thelittlegym.mobile.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import org.jsoup.nodes.Document;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by Tony on 2017/8/31.
 */

@Controller
@Slf4j

public class testCtrl  {
    @Autowired
    private IUserService userService;
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
    private  String tell(){

        return JSON.toJSONString(Msg.StatusCodeEnum.getEnum(500));
    }
}
