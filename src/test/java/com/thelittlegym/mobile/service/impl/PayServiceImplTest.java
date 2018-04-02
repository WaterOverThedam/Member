package com.thelittlegym.mobile.service.impl;

import com.thelittlegym.mobile.entity.Order;
import com.thelittlegym.mobile.service.OrderService;
import com.thelittlegym.mobile.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PayServiceImplTest {
    @Autowired
    private PayService payService;

    @Autowired
    private OrderService orderService;
    @Test
    public void create() {

        Order order = orderService.findOne("1522658731591868859");
        payService.create(order);
    }
}