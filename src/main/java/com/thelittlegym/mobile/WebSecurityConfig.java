/**
 * Created by Tony on 2017/9/2.
 */
package com.thelittlegym.mobile;

import com.thelittlegym.mobile.dao.PageLogDao;
import com.thelittlegym.mobile.entity.PageLog;
import com.thelittlegym.mobile.entity.User;
import lombok.extern.slf4j.Slf4j;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import java.util.Date;


@Configuration
@Slf4j
public class WebSecurityConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/web/**").addResourceLocations("classpath:/web/");
        super.addResourceHandlers(registry);
    }

    /**
     * 登录session key
     */
    public final static String SESSION_KEY = "user";

    @Bean
    public SecurityInterceptor getSecurityInterceptor() {
        return new SecurityInterceptor();
    }

    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration addInterceptor = registry.addInterceptor(getSecurityInterceptor());

        // 排除配置
        addInterceptor.excludePathPatterns("/exit");
        addInterceptor.excludePathPatterns("/noaccess.html");
        addInterceptor.excludePathPatterns("/forgetPass.html");
        addInterceptor.excludePathPatterns("/wxbrowser.html");
        addInterceptor.excludePathPatterns("/error.html");
        addInterceptor.excludePathPatterns("/error");
        addInterceptor.excludePathPatterns("/login/**");
        addInterceptor.excludePathPatterns("/login.html");
        addInterceptor.excludePathPatterns("/timeout.html");
        addInterceptor.excludePathPatterns("/admin/login");
        addInterceptor.excludePathPatterns("/admin/exit");
        addInterceptor.excludePathPatterns("/admin/getSetting");
        addInterceptor.excludePathPatterns("/admin/login/**");
        addInterceptor.excludePathPatterns("/test/**");
        addInterceptor.excludePathPatterns("/activities.html");
        addInterceptor.excludePathPatterns("/activity/**");
        addInterceptor.excludePathPatterns("/weixin/**");
        addInterceptor.excludePathPatterns("/wechat/**");


        //拦截配置
        addInterceptor.addPathPatterns("/**");

    }

    private class SecurityInterceptor extends HandlerInterceptorAdapter {
        @Autowired
        PageLogDao pageLogDao;
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
                throws Exception {
                log.info("..............intercepted.................");
            HttpSession session = request.getSession();
            String requestUri = request.getRequestURI();
            String linkId = request.getParameter("linkId");
            if (null != linkId && "1225".equals(linkId)) {
                session.setAttribute("linkId", linkId);
            }

            //log.info(requestUri);
            //log.info(String.valueOf(requestUri.indexOf("admin")));
            //前台
            if(requestUri.indexOf("admin") == -1) {
                Object user = session.getAttribute("user");
                if (user == null) {
                    // 跳转登录
                    String url = "/login.html";
                    response.sendRedirect(url);
                    return false;
                } else {
                    User u = (User) user;
                    PageLog pageLog = new PageLog();
                    pageLog.setCreateTime(new Date());
                    pageLog.setPageURL(requestUri);
                    pageLog.setRequestType(request.getMethod());
                    pageLog.setUserName(u.getUsername());
                    pageLog.setSearch(pageLog.toString());
                    pageLogDao.save(pageLog);
                }
            }else {
                //后台
                Object admin = session.getAttribute("admin");
                if(admin==null) {
                    response.sendRedirect("/admin/login");
                    return false;
                }
            }
            return true;
        }
    }
}
