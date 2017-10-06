package com.thelittlegym.mobile;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * Created by TONY on 2017/10/2.
 */
/*
 * 注册MyBatis分页插件PageHelper
 */
@Configuration
@Slf4j
public class MyBatisConfig {
    @Bean
    public PageHelper pageHelper() {
        //log.info("MyBatisConfiguration.pageHelper()......");
        PageHelper pageHelper = new PageHelper();
        Properties p = new Properties();
        p.setProperty("offsetAsPageNum", "true");
        p.setProperty("rowBoundsWithCount", "true");
        p.setProperty("reasonable", "true");
        pageHelper.setProperties(p);
        return pageHelper;
    }
}