package com.s.order_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class OrderResponseDto {

    private Long orderId;
    private Long productId;
    private int quantity;
    private double totalPrice;

    //product details
    private String productName;
    private double productPrice;

}
