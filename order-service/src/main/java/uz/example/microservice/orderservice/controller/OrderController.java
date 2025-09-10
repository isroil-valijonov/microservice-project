package uz.example.microservice.orderservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import uz.example.microservice.orderservice.dto.OrderRequestDto;
import uz.example.microservice.orderservice.entity.Order;
import uz.example.microservice.orderservice.service.OrderService;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order placeOrder(@RequestBody OrderRequestDto orderRequestDto) {
        return orderService.placeOrder(orderRequestDto);
    }

}
