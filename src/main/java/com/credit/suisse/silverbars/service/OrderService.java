package com.credit.suisse.silverbars.service;

import com.credit.suisse.silverbars.entities.Order;
import com.credit.suisse.silverbars.model.OrderSummary;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    ResponseEntity<Order> createOrder(Order order);

    ResponseEntity<String> cancelOrder(Long orderId);

    ResponseEntity<OrderSummary> getOrderSummary();
}
