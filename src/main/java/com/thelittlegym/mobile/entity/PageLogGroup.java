package com.thelittlegym.mobile.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by TONY on 2017/10/1.
 */
//@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@SuppressWarnings("deprecation")
public class PageLogGroup {
    @JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
    private String pageURL;
    @JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
    private String requestType;
    @JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
    private String userName;
    @JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    private Date createTime;
    @JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
    private Integer ci;
}
