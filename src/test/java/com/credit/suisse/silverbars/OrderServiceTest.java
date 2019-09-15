package com.credit.suisse.silverbars;

import com.credit.suisse.silverbars.entities.Order;
import com.credit.suisse.silverbars.model.OrderSummary;
import com.credit.suisse.silverbars.repository.OrderRepository;
import com.credit.suisse.silverbars.service.OrderService;
import com.credit.suisse.silverbars.service.OrderServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;

@RunWith(SpringRunner.class)
public class OrderServiceTest {

    @TestConfiguration
    static class OrderServiceTestConfiguration{

        @Bean
        public OrderService orderService(){
            return new OrderServiceImpl();
        }

    }


    @Autowired
    private OrderService orderService;

    @MockBean
    private OrderRepository orderRepository;

    @Before
    public void setUp(){
        Order order = new Order(1L,2.5,310.0, "BUY");
        Order order1 = new Order(1L,2.5,315.0, "BUY");

        List<Order> orders = Arrays.asList(order, order, order1);

        Mockito.when(orderRepository.save(order)).thenReturn(order);
        doNothing().when(orderRepository).deleteById(1L);
        Mockito.when(orderRepository.findAll()).thenReturn(orders);
    }

    @Test
    public void testCreateOrder(){
        Order order = new Order(1L,2.5,310.0, "BUY");
        ResponseEntity<Order> savedOrder = orderService.createOrder(order);
        assertThat(savedOrder.getBody()).isEqualTo(order);
    }

    @Test
    public void testCancelOrder(){
        Order order = new Order(1L,2.5,310.0, "BUY");
        ResponseEntity<Order> savedOrder = orderService.createOrder(order);
        assertThat(savedOrder.getBody()).isEqualTo(order);

        ResponseEntity<String> cancelledMsg = orderService.cancelOrder(1L);
        assertEquals("Order with id 1 has been cancelled successfully", cancelledMsg.getBody());
    }

    @Test
    public void testOrderSummary(){

        Order order = new Order(1L,2.5,310.0, "BUY");
        Order order1 = new Order(1L,2.5,315.0, "BUY");

        orderService.createOrder(order);
        orderService.createOrder(order);
        orderService.createOrder(order1);

        ResponseEntity<OrderSummary> orderSummaryResponseEntity = orderService.getOrderSummary();
        OrderSummary orderSummary = orderSummaryResponseEntity.getBody();

        assert orderSummary != null;
        assertEquals(2, orderSummary.getOrders().size());
        assertTrue(orderSummary.getOrders().get(0).contains("315.0"));
    }

}
