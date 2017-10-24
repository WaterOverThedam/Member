package com.thelittlegym.mobile.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by TONY on 2017/10/24.
 */
@Data
public class ParticipatorGroup {
    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    private  Date dt;
    private  String phones;
    private  String addr;
    private  String participators;
    private  String gym;

}
