package uz.example.microservice.orderservice.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;


public interface InventoryClient {

    @GetExchange("/api/inventory")
    @CircuitBreaker(name = "inventory", fallbackMethod = "inventoryFallBackMethod")
    @Retry(name = "inventory")
    boolean isInStock(@RequestParam String skuCode, @RequestParam Integer quantity);

    default boolean inventoryFallBackMethod(String skuCode, Integer quantity, Throwable throwable) {
        Logger log = LoggerFactory.getLogger(InventoryClient.class);
        log.info("Fallback triggered for skuCode {}, reason: {}", skuCode, throwable.getMessage());
        return false;
    }

}
