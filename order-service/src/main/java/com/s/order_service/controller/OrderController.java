package com.s.order_service.controller;

import com.s.order_service.Repository.OrderRepository;
import com.s.order_service.dto.OrderResponseDto;
import com.s.order_service.dto.ProductDto;
import com.s.order_service.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    //create a method to place order
    @PostMapping("/placeOrder")
    public Mono<ResponseEntity<OrderResponseDto>> placeOrder(@RequestBody Order order){

        //fetch product details from product service
        return webClientBuilder.build().get().uri("http://localhost:8081/products/" + order.getProductId()).retrieve()
                .bodyToMono(ProductDto.class).map(productDto -> {
                    OrderResponseDto responseDto=new OrderResponseDto();

                    responseDto.setProductId(order.getProductId());
                    responseDto.setQuantity(order.getQuantity());

                    //set product details
                    responseDto.setProductName(productDto.getName());
                    responseDto.setProductPrice(productDto.getPrice());
                    responseDto.setTotalPrice(order.getQuantity()* productDto.getPrice());

                   //save order detail to database
                   orderRepository.save(order);
                    responseDto.setOrderId(order.getId());
                   return ResponseEntity.ok(responseDto);
                });
    }

    //get all orders
    @GetMapping
    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

}
