package com.thelittlegym.mobile.dao;

import com.thelittlegym.mobile.entity.Activity;
import com.thelittlegym.mobile.entity.Participator;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by TONY on 2017/8/26.
 */
public interface ParticipatorDao extends JpaRepository<Participator,Integer> {
    Participator findOneByPhoneAndActid(String tel,String actId);
}
