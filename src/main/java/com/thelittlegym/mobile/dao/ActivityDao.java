package com.thelittlegym.mobile.dao;

import com.thelittlegym.mobile.entity.Activity;
import com.thelittlegym.mobile.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by TONY on 2017/8/26.
 */
public interface ActivityDao extends JpaRepository<Activity,Integer> {
    public Page<Activity> findAllByIsDeleteAndSearchLike(Boolean isDelete, String search, Pageable pageable);
}
