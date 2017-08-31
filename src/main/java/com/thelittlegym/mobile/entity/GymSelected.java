package com.thelittlegym.mobile.entity;

import lombok.Data;

/**
 * Created by hibernate on 2017/5/8.
 */
@Data
public class GymSelected {
    private String beginDate;
    private String endDate;
    private Gym gym;

}
