package com.thelittlegym.mobile.controller;

import com.thelittlegym.mobile.http.HttpAPIService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by lxg
 * on 2017/2/6.
 */
@RestController
public class HttpClientCtrl {

    @Resource
    private HttpAPIService httpAPIService;

    @RequestMapping("httpclient")
    public String test() throws Exception {
        String str = httpAPIService.doGet("http://www.baidu.com");
        System.out.println(str);
        return "hello";
    }
}
