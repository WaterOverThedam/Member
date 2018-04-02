package com.thelittlegym.mobile.controller;

import com.lly835.bestpay.model.PayResponse;
import com.thelittlegym.mobile.entity.Order;
import com.thelittlegym.mobile.entity.OrderForm;
import com.thelittlegym.mobile.enums.ResultEnum;
import com.thelittlegym.mobile.exception.MyException;
import com.thelittlegym.mobile.service.OrderService;
import com.thelittlegym.mobile.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.Map;

@Controller
@RequestMapping("/pay")
public class PayCtrl {
    @Autowired
    private OrderService orderService;

    @Autowired
    private PayService payService;

    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId,
                               @RequestParam("returnUrl") String returnUrl,
                               Map<String, Object> map) {

        //1. 查询订单
        Order order = orderService.findOne(orderId);
        if (order == null) {
            throw new MyException(ResultEnum.ORDER_NOT_EXIST);
        }
        if(order.getPayStatus().equals(1)){
            throw new MyException(ResultEnum.ORDER_PAY_REPEAT);
        }

        //2. 发起支付
        PayResponse payResponse = payService.create(order);

        map.put("payResponse", payResponse);
        map.put("returnUrl", returnUrl);

        return new ModelAndView("pay/create", map);
    }

    /**
     * 微信异步通知
     * @param notifyData
     */
    @PostMapping("/notify")
    public ModelAndView notify(@RequestBody String notifyData) {
        payService.notify(notifyData);

        //返回给微信处理结果
        return new ModelAndView("pay/success");
    }

}
