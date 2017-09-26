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
        //log.error("fuckyou!");
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
        addInterceptor.excludePathPatterns("/noaccess.html");
        addInterceptor.excludePathPatterns("/forgetPass.html");
        addInterceptor.excludePathPatterns("/wxbrowser.html");
        addInterceptor.excludePathPatterns("/error.html");
        addInterceptor.excludePathPatterns("/error");
        addInterceptor.excludePathPatterns("/login/**");
        addInterceptor.excludePathPatterns("/login.html");
        addInterceptor.excludePathPatterns("/timeout.html");
        addInterceptor.excludePathPatterns("/admin/login");
        addInterceptor.excludePathPatterns("/admin/login/**");
        //拦截配置
        addInterceptor.addPathPatterns("/**");

    }

    private class SecurityInterceptor extends HandlerInterceptorAdapter {
        @Autowired
        PageLogDao pageLogDao;
        PageLog pageLog = new PageLog();
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
            Object admin = session.getAttribute("admin");
            if(requestUri.indexOf("admin")!=-1){
            //后台
                if(admin==null) {
                    String url = "/admin/login";
                    response.sendRedirect(url);
                    return false;
                }else {
                    return true;
                }
            }else{
             //前台
                Object user = session.getAttribute("user");
                if (user == null) {
                    // 跳转登录
                    String url = "/login.html";
                    response.sendRedirect(url);
                    return false;
                }else {
                    User u = (User) user;
                    pageLog.setCreateTime(new Date());
                    pageLog.setPageURL(requestUri);
                    pageLog.setRequestType(request.getMethod());
                    pageLog.setUserName(u.getUsername());
                    pageLog.setSearch(pageLog.toString());
                    pageLogDao.save(pageLog);
                }
            }

            return true;
        }
    }
}
