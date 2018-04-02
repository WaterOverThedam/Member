package com.thelittlegym.mobile.dao;

import com.thelittlegym.mobile.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDao extends JpaRepository<Order,String>{

}
