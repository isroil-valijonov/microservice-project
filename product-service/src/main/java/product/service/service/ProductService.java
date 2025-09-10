package product.service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import product.service.dto.ProductRequestDto;
import product.service.entity.Product;
import product.service.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    public Product createProduct(ProductRequestDto productRequestDto) {
        if (productRequestDto.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0");
        }
        if (productRequestDto.getName().isEmpty()) {
            throw new IllegalArgumentException("Product name must not be empty");
        }
        if (productRequestDto.getDescription() == null) {
            throw new IllegalArgumentException("Description must not be null");
        }

        Product product = new Product(productRequestDto.getName(), productRequestDto.getDescription(), productRequestDto.getPrice());
        log.info("Product created was successfully");
        return productRepository.save(product);

    }


    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
