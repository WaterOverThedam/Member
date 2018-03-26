package com.thelittlegym.mobile.service;

import com.thelittlegym.mobile.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by 廖师兄
 * 2017-06-11 18:23
 */
public interface OrderService {

    /** 创建订单. */
    Order create(Order order);

    /** 查询单个订单. */
    Order findOne(String orderId);

    /** 查询订单列表. */
    Page<Order> findList(String buyerOpenid, Pageable pageable);
    /** 查询订单列表. */
    Page<Order> findList(Pageable pageable);

    /** 取消订单. */
    Order cancel(Order order);

    /** 完结订单. */
    Order finish(Order order);

    /** 支付订单. */
    Order paid(Order order);



}
