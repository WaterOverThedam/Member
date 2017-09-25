package com.thelittlegym.mobile.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by hibernate on 2017/5/11.
 */
@Controller
@RequestMapping("/exit")
public class ExitCtrl {
    //会员退出
    @RequestMapping(value = "",method = RequestMethod.GET)
    public String activities(HttpServletRequest request, Model model) throws Exception {
        HttpSession session = request.getSession();
        Object hideSession = session.getAttribute("hide");
        Object adminSession = session.getAttribute("admin");

        //后台登录，退出到入口界面
        if (null != adminSession){
            return "redirect:/admin/simulation";
        }

        //退出判断是否linkId进入
        if ( null != hideSession){
            session.invalidate();
            return "redirect:/?linkId=1225";
        }
        session.setAttribute("user",null);
        session.invalidate();
        return "redirect:/login.html";
    }
}
