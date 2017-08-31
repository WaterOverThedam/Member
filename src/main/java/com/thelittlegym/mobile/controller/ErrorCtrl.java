package com.thelittlegym.mobile.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by hibernate on 2017/5/23.
 */
@Controller
public class ErrorCtrl {
    @RequestMapping("/")
    public String errorPage(){

        return "redirect:error.html";
    }
}
