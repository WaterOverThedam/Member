package com.thelittlegym.mobile.service;

import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundResponse;
import com.thelittlegym.mobile.entity.Order;

/**
 * 支付
 * Created by 廖师兄
 * 2017-07-04 00:53
 */
public interface PayService {

    PayResponse create(Order order);

    PayResponse notify(String notifyData);

}
