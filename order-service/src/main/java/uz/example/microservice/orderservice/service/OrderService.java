package uz.example.microservice.orderservice.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import uz.example.microservice.orderservice.client.InventoryClient;
import uz.example.microservice.orderservice.dto.OrderRequestDto;
import uz.example.microservice.orderservice.entity.Order;
import uz.example.microservice.orderservice.event.OrderPlacedEvent;
import uz.example.microservice.orderservice.event.UserDetails;
import uz.example.microservice.orderservice.repository.OrderRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    public Order placeOrder(OrderRequestDto orderRequestDto) {
        boolean inStock = false;

        try {
            inStock = inventoryClient.isInStock(
                    orderRequestDto.getSkuCode(),
                    orderRequestDto.getQuantity()
            );
        } catch (Exception ex) {
            log.error("Inventory service is unavailable or failed: {}", ex.getMessage());
            // Fallback: decide what to do, for example, treat as out of stock
            throw new RuntimeException("Could not verify stock status due to service failure", ex);
        }

        if (inStock) {
            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setPrice(orderRequestDto.getPrice());
            order.setSkuCode(orderRequestDto.getSkuCode());
            order.setQuantity(orderRequestDto.getQuantity());

            Order savedOrder = orderRepository.save(order);

            // Dummy user for testing
            orderRequestDto.setUserDetails(new UserDetails("A", "B", "abc@gmail.com"));

            OrderPlacedEvent event = new OrderPlacedEvent(
                    savedOrder.getOrderNumber(),
                    orderRequestDto.getUserDetails().getEmail()
            );

            log.info("Sending OrderPlacedEvent to Kafka: {}", event);
            kafkaTemplate.send("order-placed", event);
            return savedOrder;
        } else {
            throw new RuntimeException("Product with skuCode " + orderRequestDto.getSkuCode() + " is not in stock!");
        }
    }
}
