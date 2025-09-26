package uz.example.microservice.orderservice.client;

import org.springframework.stereotype.Component;

@Component
public class InventoryClientFallback implements InventoryClient {
    @Override
    public boolean isInStock(String skuCode, Integer quantity) {
        return false; // fallbackda out of stock deymiz
    }
}
