package com.credit.suisse.silverbars.service;

import com.credit.suisse.silverbars.entities.Order;
import com.credit.suisse.silverbars.model.OrderSummary;
import com.credit.suisse.silverbars.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public ResponseEntity<Order> createOrder(Order order) {
        Order orderSaved = orderRepository.save(order);
        return new ResponseEntity<>(orderSaved, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> cancelOrder(Long orderId) {
        try{
            orderRepository.deleteById(orderId);
        }catch(EmptyResultDataAccessException emptyResultDataAccessException){
            return new ResponseEntity<>(String.format("Order with id %d does not exist ", orderId), HttpStatus.OK);
        }

        return new ResponseEntity<>(String.format("Order with id %d has been cancelled successfully", orderId), HttpStatus.OK);
    }

    // Streams facilitate chaining of intermediary process operations and a terminal operation to process any collection.
    // This way it is more readable and eliminates traditional boilerplate and verbose code which is difficult to understand at a glance, because of multiple nested control-flow statements
    @Override
    public ResponseEntity<OrderSummary> getOrderSummary() {
        List<String> sortedOrders = new ArrayList<>();

        List<Order> orders = orderRepository.findAll();

        List<Order> buyOrdersSorted = orders.stream()
                .filter(o -> "BUY".equalsIgnoreCase(o.getType()))
                .sorted(Comparator.comparing(Order::getPricePerKg, Comparator.reverseOrder()))
                .collect(Collectors.toList());

        List<Order> sellOrdersSorted = orders.stream()
                .filter(o -> "SELL".equalsIgnoreCase(o.getType()))
                .sorted(Comparator.comparing(Order::getPricePerKg))
                .collect(Collectors.toList());


        List<Order> sortedOrdersList = Stream.
                of(sellOrdersSorted, buyOrdersSorted)
                .flatMap(List::stream).collect(Collectors.toList());

        Map<String, Map<Double, Double>> liveBoard = sortedOrdersList.stream()
                .collect(groupingBy(Order::getType, groupingBy(Order::getPricePerKg, LinkedHashMap::new,
                Collectors.summingDouble(Order::getQuantity))));


        //This is just to meet the  Acceptance Criteria :) . I would prefer returning a json of orders and let the UI format as needed
        liveBoard.keySet().forEach(t -> liveBoard.get(t).forEach((p, q) -> sortedOrders.add(String.format("%s %s Kg For %s", t, q, p))));

        OrderSummary orderSummary = new OrderSummary();
        orderSummary.setOrders(sortedOrders);

        return new ResponseEntity<>(orderSummary, HttpStatus.OK);
    }
}
