package com.thelittlegym.mobile.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by hibernate on 2017/5/25.
 */
@Entity
@Data
@Table(name="participator")
public class Participator {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String familyTitle;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = true)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activityId", nullable = true)
    private Activity activity;
    private Date createTime;


}
