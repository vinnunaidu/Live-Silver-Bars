package com.credit.suisse.silverbars;

import com.credit.suisse.silverbars.service.OrderService;
import com.credit.suisse.silverbars.service.OrderServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication

public class LiveSilverBarsApplication {

	public static void main(String[] args) {
		SpringApplication.run(LiveSilverBarsApplication.class, args);
	}

	@Bean
	public OrderService orderService(){
		return new OrderServiceImpl();
	}

}
