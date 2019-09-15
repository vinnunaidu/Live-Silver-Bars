package com.credit.suisse.silverbars;

import com.credit.suisse.silverbars.entities.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LiveSilverBarsApplicationTests {

	@Test
	public void contextLoads() {
	}

	private Order createOrder(){
		Order order = new Order();
		order.setUserId(1L);
		order.setQuantity(2.5);
		order.setPricePerKg(310.0);
		order.setType("BUY");

		return order;
	}
}
