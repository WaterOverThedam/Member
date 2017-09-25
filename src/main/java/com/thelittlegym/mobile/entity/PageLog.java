package com.thelittlegym.mobile.entity;

import lombok.Data;
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
    private Date createTime;
    private String search;

    @Override
    public String toString() {
        return "|"+ pageURL + "|"+ requestType + "|"
                +userName + "|"+createTime;
    }
}

