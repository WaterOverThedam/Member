package com.thelittlegym.mobile.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by TONY on 2017/9/23.
 */
@Entity
@Data
public class PageLog {
    @Id
    @GeneratedValue
    private Integer id;
    private String pageURL;
    private String requestType;
    private String userName;
    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    private Date createTime;
    private String search;

    @Override
    public String toString() {
        return "|"+ pageURL + "|"+ requestType + "|"
                +userName + "|"+createTime;
    }
}

