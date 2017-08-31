package com.thelittlegym.mobile.dao;

import com.thelittlegym.mobile.entity.Activity;
import com.thelittlegym.mobile.entity.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by TONY on 2017/8/26.
 */
public interface FeedbackDao extends JpaRepository<Feedback,Integer> {
     public long countByHandled(Boolean handled);
     public Page<Feedback> findAllByHandled(Boolean handled, Pageable pageable);

}
