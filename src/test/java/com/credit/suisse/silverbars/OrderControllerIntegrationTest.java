package com.credit.suisse.silverbars;

import com.credit.suisse.silverbars.entities.Order;
import com.credit.suisse.silverbars.repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = LiveSilverBarsApplication.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
public class OrderControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void testCreateOrder() throws Exception {
        Order order = new Order(1L,2.5,310.0, "BUY");
        mvc.perform(post("/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(order))
                );
        Order orderFromDb = orderRepository.getOne(1L);
        assertEquals(order.getPricePerKg(), orderFromDb.getPricePerKg());

    }

    @Test
    public void testCancelOrder() throws Exception {
        Order order1 = new Order(1L,2.5,310.0, "BUY");
        Order order2 = new Order(1L,2.5,310.0, "BUY");

        mvc.perform(post("/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(order1))
        );

        mvc.perform(post("/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(order2))
        );

        List<Order> totalOrders = orderRepository.findAll();
        assertEquals(2, totalOrders.size());

        mvc.perform(delete("/order/{id}", totalOrders.get(1).getOrderId()))
                .andExpect(status().isOk())
                .andExpect(content().string("Order with id 2 has been cancelled successfully"));

        List<Order> totalOrdersAfterCancelOrder = orderRepository.findAll();
        assertEquals(1, totalOrdersAfterCancelOrder.size());
    }

    @Test
    public void testBuyOrderSummary() throws Exception{
        postOrders("BUY");

        mvc.perform(get("/order/ordersSummary")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.orders", hasSize(3)))
                    .andExpect(jsonPath("$.orders[0]", is("BUY 2.5 Kg For 320.0") ));

    }

    @Test
    public void testSellOrderSummary() throws Exception{
        postOrders("SELL");

        mvc.perform(get("/order/ordersSummary")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orders", hasSize(3)))
                .andExpect(jsonPath("$.orders[0]", is("SELL 5.0 Kg For 310.0") ));

    }

    private void postOrders(String type) throws Exception {
        Order order1 = new Order(1L, 2.5, 310.0, type);
        Order order2 = new Order(1L, 2.5, 315.0, type);
        Order order3 = new Order(1L, 2.5, 320.0, type);

        mvc.perform(post("/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(order1))
        );

        mvc.perform(post("/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(order1))
        );

        mvc.perform(post("/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(order2))
        );

        mvc.perform(post("/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(order3))
        );

        List<Order> totalOrders = orderRepository.findAll();
        assertEquals(4, totalOrders.size());
    }
}
