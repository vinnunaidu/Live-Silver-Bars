package com.credit.suisse.silverbars.service;

import com.credit.suisse.silverbars.entities.Order;
import com.credit.suisse.silverbars.model.OrderSummary;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    Order createOrder(Order order);

    String cancelOrder(Long orderId);

    OrderSummary getOrderSummary();
}
