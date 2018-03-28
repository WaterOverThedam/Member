package com.thelittlegym.mobile.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by hibernate on 2017/5/17.
 */

@Data
public class OasisMSG {
    @Id
    private String id;
    private String  openId;
    private String  hao;
    private String  sheng;
    private String  hz;
    private String  date;
    private String  first;
    private String  remark;

}
