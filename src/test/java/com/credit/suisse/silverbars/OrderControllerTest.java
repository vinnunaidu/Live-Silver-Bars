package com.credit.suisse.silverbars;

import com.credit.suisse.silverbars.controller.OrderController;
import com.credit.suisse.silverbars.entities.Order;
import com.credit.suisse.silverbars.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private OrderService service;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void testCreateOrder() throws Exception {

        Order order = new Order(1L,2.5,310.0, "BUY");
        Order createdOrder = new Order(1L,1L,2.5,310.0, "BUY");
        ResponseEntity<Order> orderResponse = new ResponseEntity<>(createdOrder, HttpStatus.OK);

        given(service.createOrder(order)).willReturn(orderResponse);


        mvc.perform(post("/order")
                .content(mapper.writeValueAsString(order))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").exists()
        );
    }

    @Test
    public void testCancelOrder() throws Exception{

        ResponseEntity<String> orderResponse = new ResponseEntity<>("Order with id 1 has been cancelled successfully", HttpStatus.OK);

        given(service.cancelOrder(1L)).willReturn(orderResponse);

         mvc.perform(delete("/order/1"))
                           .andExpect(status().isOk())
                           .andExpect(content().string("Order with id 1 has been cancelled successfully"));

    }


}
