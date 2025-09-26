package uz.example.microservice.orderservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Order> placeOrder(@RequestBody OrderRequestDto requestDto) {
        Order order = orderService.placeOrder(requestDto);
        return ResponseEntity.ok(order);
    }

}
