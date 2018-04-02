package com.thelittlegym.mobile.service.impl;

import com.thelittlegym.mobile.entity.Order;
import com.thelittlegym.mobile.entity.OrderForm;
import com.thelittlegym.mobile.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {
    @Autowired
    private OrderService orderService;
    @Test
    public void create() {
        OrderForm orderForm = new OrderForm();
        orderForm.setPhone("13916478060");
        orderForm.setAddress("thelittlegym");
        orderForm.setItems("Fee for enrolling activity");
        orderForm.setName("Tony");
        orderForm.setOpenid("o2KB1jt6YK_GVlFTl7DfE53wRphg");
        orderForm.setAmount(new BigDecimal(0.1));
        Order order = orderService.create(orderForm);
        log.info("【创建订单】result={}", order);
        Assert.assertNotNull(order);
    }
    @Test
    public void cancel() {

        Order order = orderService.cancel("1522658731591868859");
        log.info("【创建订单】result={}", order);
        Assert.assertNotNull(order);
    }
}