package com.thelittlegym.mobile.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by TONY on 2017/9/25.
 */
@Slf4j
@Controller
@RequestMapping("/error")
public class MyErrorCtrl implements ErrorController {

    @Override
    public String getErrorPath() {
        return "";
    }

    @RequestMapping(value="")
    public String error(HttpServletRequest request) {
        HttpStatus status = getStatus(request);

        if(status.equals(HttpStatus.NOT_FOUND)){
            return  "redirect:/404.html";
        }
        if(status.equals(HttpStatus.INTERNAL_SERVER_ERROR)){
            return  "redirect:/timeout.html";
        }
        if(status.equals(HttpStatus.GATEWAY_TIMEOUT)){
            return  "redirect:/timeout.html";
        }
        if(status.equals(HttpStatus.REQUEST_TIMEOUT)){
            return  "redirect:/timeout.html";
        }

        return  "redirect:/err.html";

    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request
                .getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        try {
            return HttpStatus.valueOf(statusCode);
        }
        catch (Exception ex) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }


}