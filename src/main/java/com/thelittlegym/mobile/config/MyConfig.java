package com.thelittlegym.mobile.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


/**
 * Created by TONY on 2017/10/4.
 */
@Configuration
@ConfigurationProperties(prefix="test")
@PropertySource( "classpath:application-dev.yml")
@Data
public class MyConfig {
  private String name;
  private Integer age;


}

