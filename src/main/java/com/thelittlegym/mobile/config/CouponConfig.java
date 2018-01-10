package com.thelittlegym.mobile.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


/**
 * Created by TONY on 2017/10/4.
 */
@Configuration
@ConfigurationProperties(prefix="coupon")
@PropertySource( "classpath:application-dev.yml")
@Data
public class CouponConfig {
  private String useCode;
  private String useCode_2;
  private String useCode_3;

}

