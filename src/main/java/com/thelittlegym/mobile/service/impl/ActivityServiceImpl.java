package com.thelittlegym.mobile.service.impl;

import com.thelittlegym.mobile.dao.ActivityDao;
import com.thelittlegym.mobile.entity.Activity;
import com.thelittlegym.mobile.service.IActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by TONY on 2017/8/27.
 */
@Service
public class ActivityServiceImpl implements IActivityService {
    @Autowired
    private ActivityDao activityDao;

    @Override
    public Page<Activity> findAllByIsDeleteAndNameLike(Boolean isDelete, String name,Pageable pageable) {
        return activityDao.findAllByIsDeleteAndNameLike(isDelete,name,pageable);
    }

    @Override
    public Activity findOne(Integer id) {
        return activityDao.findOne(id);
    }
}
