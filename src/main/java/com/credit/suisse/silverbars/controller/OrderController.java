package com.credit.suisse.silverbars.controller;

import com.credit.suisse.silverbars.entities.Order;
import com.credit.suisse.silverbars.model.OrderSummary;
import com.credit.suisse.silverbars.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping()
    public ResponseEntity<Order> createOrder(@RequestBody Order order){

        return new ResponseEntity<>(orderService.createOrder(order), HttpStatus.OK);

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> cancelOrder(@PathVariable Long id){
        return new ResponseEntity<>(orderService.cancelOrder(id), HttpStatus.OK);

    }

    @GetMapping(value = "/ordersSummary")
    public ResponseEntity<OrderSummary> getOrderSummary(){
        return new ResponseEntity<>(orderService.getOrderSummary(), HttpStatus.OK);
    }
}
