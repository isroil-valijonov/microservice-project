package uz.example.microservice.orderservice.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import uz.example.microservice.orderservice.client.InventoryClient;
import uz.example.microservice.orderservice.client.UserClient;
import uz.example.microservice.orderservice.dto.OrderRequestDto;
import uz.example.microservice.orderservice.dto.UserDto;
import uz.example.microservice.orderservice.entity.Order;
import uz.example.microservice.orderservice.event.OrderPlacedEvent;
import uz.example.microservice.orderservice.repository.OrderRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    @Qualifier("uz.example.microservice.orderservice.client.InventoryClient")
    private final InventoryClient inventoryClient;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;
    @Qualifier("uz.example.microservice.orderservice.client.UserClient")
    private final UserClient userClient;

    public Order placeOrder(OrderRequestDto orderRequestDto) {

        // 1. Userni tekshirish
        UserDto user = userClient.getUserById(orderRequestDto.getUserId());
        if (user == null) {
            throw new RuntimeException("User not found with id " + orderRequestDto.getUserId());
        }

        // 2. Inventory tekshirish
        boolean inStock = inventoryClient.isInStock(
                orderRequestDto.getSkuCode(),
                orderRequestDto.getQuantity()
        );

        if (!inStock) {
            throw new RuntimeException("Product with skuCode " + orderRequestDto.getSkuCode() + " is not in stock!");
        }

        // 3. Orderni saqlash
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setPrice(orderRequestDto.getPrice());
        order.setSkuCode(orderRequestDto.getSkuCode());
        order.setQuantity(orderRequestDto.getQuantity());

        Order savedOrder = orderRepository.save(order);

        // 4. Kafka event jo‘natish
        OrderPlacedEvent event = OrderPlacedEvent.newBuilder()
                .setOrderNumber(savedOrder.getOrderNumber())
                .setEmail(user.getEmail())
                .build();

        log.info("Sending OrderPlacedEvent to Kafka: {}", event);
        kafkaTemplate.send("order-placed", event);

        return savedOrder;
    }

}
