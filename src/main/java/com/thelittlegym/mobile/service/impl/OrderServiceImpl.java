package com.thelittlegym.mobile.service.impl;

import com.thelittlegym.mobile.dao.OrderDao;
import com.thelittlegym.mobile.entity.Order;
import com.thelittlegym.mobile.entity.OrderForm;
import com.thelittlegym.mobile.enums.OrderStatusEnum;
import com.thelittlegym.mobile.enums.PayStatusEnum;
import com.thelittlegym.mobile.enums.ResultEnum;
import com.thelittlegym.mobile.exception.MyException;
import com.thelittlegym.mobile.service.OrderService;
import com.thelittlegym.mobile.service.PayService;
import com.thelittlegym.mobile.service.PushMessageService;
import com.thelittlegym.mobile.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by 廖师兄
 * 2017-06-11 18:43
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private PayService payService;

    @Autowired
    private PushMessageService pushMessageService;


    @Override
    @Transactional
    public Order create(OrderForm orderForm) {
        Order order = new Order();
        String orderId = KeyUtil.genUniqueKey();
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        order.setOrderId(orderId);
        order.setDetail(orderForm.getItems());
        order.setBuyerName(orderForm.getName());
        order.setBuyerPhone(orderForm.getPhone());
        order.setBuyerAddress(orderForm.getAddress());
        order.setBuyerOpenid(orderForm.getOpenid());
        order.setOrderAmount(orderForm.getAmount());
        order.setPayStatus(PayStatusEnum.WAIT.getCode());
        order.setOrderStatus(OrderStatusEnum.NEW.getCode());
        return order;
    }


    @Override
    @Transactional
    public Order cancel(String orderId) {
        Order order = orderDao.findOne(orderId);
        if (order == null) {
            log.error("【取消订单】查不到改订单, orderId={}", orderId);
            throw new MyException(ResultEnum.ORDER_NOT_EXIST);
        }

        //判断订单状态(finished,cancel can't)
        if (!order.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【取消订单】订单状态不正确, orderId={}, orderStatus={}", order.getOrderId(), order.getOrderStatus());
            throw new MyException(ResultEnum.ORDER_STATUS_ERROR);
        }


        //修改订单状态
        order.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        Order updateResult = orderDao.save(order);
        if (updateResult == null) {
            log.error("【取消订单】更新失败, order={}", order);
            throw new MyException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        //如果已支付, 需要退款
        if (order.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())) {
            payService.refund(order);
        }

        return updateResult;
    }

    @Override
    @Transactional
    public Order finish(String orderId) {
        Order order = orderDao.findOne(orderId);
        if (order == null) {
            log.error("【finish订单】查不到改订单, orderId={}", orderId);
            throw new MyException(ResultEnum.ORDER_NOT_EXIST);
        }
        order.setOrderStatus(OrderStatusEnum.FINISHED.getCode());

        //推送微信模版消息
        pushMessageService.orderStatus(order,"Is-sAuw0gZGlDXxFrGlVRZsLXY3nq8wtxe0rXZQ3pjY");

        return orderDao.save(order);
    }


    @Override
    @Transactional
    public Order paid(String orderId) {
        Order order = orderDao.findOne(orderId);
        if (order == null) {
            log.error("【Pay订单】查不到改订单, orderId={}", orderId);
            throw new MyException(ResultEnum.ORDER_NOT_EXIST);
        }

        order.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        pushMessageService.orderStatus(order,"Is-sAuw0gZGlDXxFrGlVRZsLXY3nq8wtxe0rXZQ3pjY");
        return orderDao.save(order);
    }

    @Override
    @Transactional
    public Order paid(Order order) {
        order.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        //推送微信模版消息
        pushMessageService.orderStatus(order,"Is-sAuw0gZGlDXxFrGlVRZsLXY3nq8wtxe0rXZQ3pjY");
        return orderDao.save(order);
    }


    /** 查询单个订单. */
    @Override
    public Order findOne(String orderId){
        return orderDao.findOne(orderId);
    };

    /** 查询订单列表. */
    @Override
    public Page<Order> findList(String buyerOpenid, Pageable pageable){
        return null;
    };
    /** 查询订单列表. */
    @Override
    public Page<Order> findList(Pageable pageable){ return null; };
}
