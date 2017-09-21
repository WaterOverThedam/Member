package com.thelittlegym.mobile.service.impl;

import com.thelittlegym.mobile.dao.FeedbackDao;
import com.thelittlegym.mobile.entity.Feedback;
import com.thelittlegym.mobile.service.IFeedbackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import java.util.Date;


/**
 * Created by hibernate on 2017/4/21.
 */
@Service
@Slf4j
public class FeedbackServiceImpl implements IFeedbackService {
    @Autowired
    private FeedbackDao feedBackDao;

    @Override
    public Feedback save(Feedback feedback) {
        return  feedBackDao.save(feedback);
    }

    @Override
    public Page<Feedback> findAllByHandled(Integer type,Pageable pageable){
        Page<Feedback> feedbacks =null;
        log.info(String.valueOf(type));
        switch(type) {
            case 0:
                //未处理
                feedbacks = feedBackDao.findAllByHandled(false,pageable);
                break;
            case 1:
                //已处理
                feedbacks =  feedBackDao.findAllByHandled(true,pageable);
                break;
            case -1:
                //全部
                feedbacks =  feedBackDao.findAll(pageable);
            default:break;
        }
        return feedbacks;
    }

    @Override
    public Feedback findOne(Integer id)   {
        return feedBackDao.findOne(id);
    }

    @Override
    public void handle(Integer id) {
        Feedback feedback = feedBackDao.getOne(id);
        if (feedback != null){
            feedback.setHandled(true);
            feedback.setHandledTime(new Date());
            feedBackDao.save(feedback);
        }
    }

    @Override
    public long handledCount(Boolean handled) {
        return feedBackDao.countByHandled(handled);
    }
}
