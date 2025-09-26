package uz.example.microservice.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDto {
    private String orderNumber;
    private String skuCode;
    private BigDecimal price;
    private Integer quantity;
    private Long userId;
}
