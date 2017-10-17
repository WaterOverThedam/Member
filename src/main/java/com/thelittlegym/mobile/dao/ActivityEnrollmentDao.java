package com.thelittlegym.mobile.dao;

import com.thelittlegym.mobile.entity.Activity;
import com.thelittlegym.mobile.entity.ActivityEnrollment;
import com.thelittlegym.mobile.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by TONY on 2017/10/7.
 */
public interface ActivityEnrollmentDao extends JpaRepository<ActivityEnrollment,Integer> {

}
