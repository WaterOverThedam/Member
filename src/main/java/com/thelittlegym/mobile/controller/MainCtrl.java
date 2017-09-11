package com.thelittlegym.mobile.controller;

/**
 * Created by Tony on 2017/9/10.
 */
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.thelittlegym.mobile.WebSecurityConfig;
import com.thelittlegym.mobile.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class MainCtrl {
    @GetMapping("/")
    public String index() {
        return "redirect:/index";
    }
}
