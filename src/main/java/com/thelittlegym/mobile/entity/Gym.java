package com.thelittlegym.mobile.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by hibernate on 2017/4/13.
 */
@Entity
@Data
@Table(name="gym")
public class Gym {
    @Id
    private String gymId;
    private String gymName;

}
