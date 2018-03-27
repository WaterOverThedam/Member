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
    private Integer id;
    private String  openId;

}
