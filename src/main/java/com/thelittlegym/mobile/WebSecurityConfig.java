/**
 * Created by Tony on 2017/9/2.
 */
package com.thelittlegym.mobile;

import lombok.extern.slf4j.Slf4j;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

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

        //拦截配置
        addInterceptor.addPathPatterns("/**");

        // 排除配置
        addInterceptor.excludePathPatterns("/noaccess.html");
        addInterceptor.excludePathPatterns("/forgetPass.html");
        addInterceptor.excludePathPatterns("/wxbrowser.html");
        addInterceptor.excludePathPatterns("/error.html");
        addInterceptor.excludePathPatterns("/login/**");
        addInterceptor.excludePathPatterns("/login.html");
        addInterceptor.excludePathPatterns("/timeout.html");
        addInterceptor.excludePathPatterns("/admin/**");
        addInterceptor.excludePathPatterns("/admin");


    }

    private class SecurityInterceptor extends HandlerInterceptorAdapter {

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
                throws Exception {
            HttpSession session = request.getSession();
            String requestUri = request.getRequestURI();
            String linkId = request.getParameter("linkId");
            if (null != linkId && "1225".equals(linkId)) {
                session.setAttribute("linkId", linkId);
            }

            Object user = session.getAttribute("user");
            //log.info(requestUri);
            if (user == null) {
                // 跳转登录
                String url = "/login.html";
                response.sendRedirect(url);
                return false;
            }
            return true
                    ;
        }
    }
}
