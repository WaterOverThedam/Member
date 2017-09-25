package com.thelittlegym.mobile.entity;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by TONY on 2017/9/25.
 */
@Entity
@Data
public class BlackList {
    @Id
    String username;
}
