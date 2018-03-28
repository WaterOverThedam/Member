package com.thelittlegym.mobile.service.impl;

import com.thelittlegym.mobile.entity.Order;
import com.thelittlegym.mobile.service.OrderService;
import com.thelittlegym.mobile.service.PayService;
import com.thelittlegym.mobile.service.PushMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by 廖师兄
 * 2017-06-11 18:43
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

 
    @Autowired
    private PayService payService;

    @Autowired
    private PushMessageService pushMessageService;


    @Override
    @Transactional
    public Order create(Order order) {



        return order;
    }


    @Override
    @Transactional
    public Order cancel(Order order) {


        return order;
    }

    @Override
    @Transactional
    public Order finish(Order order) {

        //推送微信模版消息
        pushMessageService.orderStatus(order,"Is-sAuw0gZGlDXxFrGlVRZsLXY3nq8wtxe0rXZQ3pjY");

        return order;
    }

    @Override
    @Transactional
    public Order paid(Order order) {


        return order;
    }

    /** 查询单个订单. */
    @Override
    public Order findOne(String orderId){
        return null;

    };

    /** 查询订单列表. */
    @Override
    public Page<Order> findList(String buyerOpenid, Pageable pageable){
        return null;
    };
    /** 查询订单列表. */
    @Override
    public Page<Order> findList(Pageable pageable){
        return null;

    };
}
