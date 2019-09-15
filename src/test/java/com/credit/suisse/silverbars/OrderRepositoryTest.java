package com.credit.suisse.silverbars;

import com.credit.suisse.silverbars.entities.Order;
import com.credit.suisse.silverbars.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;


@RunWith(SpringRunner.class)
@DataJpaTest
public class OrderRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OrderRepository orderRepository;



    @Test
    public void testSaveOrder(){
        Order order = new Order(1L,2.5,310.0, "BUY");
        Order savedOrder = entityManager.persist(order);
        assertNotNull(savedOrder);

        Optional<Order> orderFromDb = orderRepository.findById(savedOrder.getOrderId());
        orderFromDb.ifPresent(value -> assertThat(order).isEqualTo(value));
    }

    @Test
    public void testDeleteOrder(){
        Order order = new Order(1L,2.5,310.0, "BUY");
        orderRepository.save(order);
        orderRepository.deleteById(1L);
        Optional<Order> deleted =  orderRepository.findById(1L);
        assertFalse(deleted.isPresent());
    }

}
