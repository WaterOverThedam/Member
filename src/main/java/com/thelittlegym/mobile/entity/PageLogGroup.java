package com.thelittlegym.mobile.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by TONY on 2017/10/1.
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageLogGroup {
    private String pageURL;
    private String requestType;
    private String userName;
    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    private Date createTime;
    private Integer ci;
}
