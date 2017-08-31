package com.thelittlegym.mobile.service;


import com.thelittlegym.mobile.entity.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * Created by hibernate on 2017/4/21.
 */
public interface IFeedbackService {
    public void save(Feedback feedback);
    public Page<Feedback> findAllByHandled(Integer type,Pageable pageable);
    public Feedback findOne(Integer id);
    public void handle(Integer id);
    public long handledCount(Boolean handled);
}
