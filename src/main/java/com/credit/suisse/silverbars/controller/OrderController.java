package com.credit.suisse.silverbars.controller;

import com.credit.suisse.silverbars.entities.Order;
import com.credit.suisse.silverbars.model.OrderSummary;
import com.credit.suisse.silverbars.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping()
    public ResponseEntity<Order> createOrder(@RequestBody Order order){

        return orderService.createOrder(order);

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> cancelOrder(@PathVariable Long id){
        return orderService.cancelOrder(id);

    }

    @GetMapping(value = "/ordersSummary")
    public ResponseEntity<OrderSummary> getOrderSummary(){

        return orderService.getOrderSummary();
    }
}
