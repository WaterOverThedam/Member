package com.thelittlegym.mobile.controller;


import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import org.jsoup.nodes.Document;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Tony on 2017/8/31.
 */

@Controller
@Slf4j

public class testCtrl  {
    @GetMapping(value = "/say")
    private String say(ModelMap map){
        ClassPathResource res = new ClassPathResource("static/common_admin.html");
        //System.out.println(this.getClass().getResource("/").getPath());

        try {
            InputStream input = res.getInputStream();
            Document   doc = Jsoup.parse(input,"UTF-8","");
            log.error(doc.toString());
            map.addAttribute("head", doc.select("meta,link").toString());
            map.addAttribute("menu", doc.select(".item").toString());
            return "test";
        } catch (IOException e) {
            e.printStackTrace();
            return  null;
        }

    }
}
