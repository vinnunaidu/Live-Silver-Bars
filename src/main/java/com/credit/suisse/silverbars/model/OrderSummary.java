package com.credit.suisse.silverbars.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderSummary {

    List<String> orders = new ArrayList<>();

}
