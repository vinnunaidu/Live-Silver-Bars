package com.credit.suisse.silverbars.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="Orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId ;
    private Long userId;
    private Double quantity;
    private Double pricePerKg;
    private String type;

    public Order(Long userId, Double quantity, Double pricePerKg, String type) {
        this.userId = userId;
        this.quantity = quantity;
        this.pricePerKg = pricePerKg;
        this.type = type;
    }
}
