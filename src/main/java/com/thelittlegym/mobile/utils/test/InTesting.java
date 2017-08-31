package com.thelittlegym.mobile.utils.test;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by hibernate on 2017/6/22.
 */
public class InTesting {
    public boolean  isTrue(HttpServletRequest request){
        return request.getRequestURL().indexOf("localhost") > 0 || request.getRequestURL().indexOf("test") > 0;
    }
}
