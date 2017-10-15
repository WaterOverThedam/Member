package com.thelittlegym.mobile.dao;

import com.thelittlegym.mobile.entity.Activity;
import com.thelittlegym.mobile.entity.Participator;
import com.thelittlegym.mobile.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by TONY on 2017/8/26.
 */
public interface ParticipatorDao extends JpaRepository<Participator,Integer> {

}
