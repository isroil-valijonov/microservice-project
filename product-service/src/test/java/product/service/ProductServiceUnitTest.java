package product.service;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import product.service.dto.ProductRequestDto;
import product.service.entity.Product;
import product.service.repository.ProductRepository;
import product.service.service.ProductService;

import java.math.BigDecimal;


@SpringBootTest
public class ProductServiceUnitTest {
    private ProductService productService;
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        productService = new ProductService(productRepository);
    }

    @Test
    void shouldCreateProductSuccessfully() {

        ProductRequestDto productRequestDto = new ProductRequestDto(
                "Test Product", "High quality phone", new BigDecimal(1100)
        );
        Product product = new Product(
                "Test Product", "High quality phone", new BigDecimal(1100)
        );

        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product createdProduct = productService.createProduct(productRequestDto);

        assertThat(createdProduct).isNotNull();
        assertThat(createdProduct.getName()).isEqualTo("Test Product");
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void shouldThrowExceptionWhenPriceIsNegative() {

        ProductRequestDto productRequestDto = new ProductRequestDto(
                "Test Product", "High quality phone", BigDecimal.ZERO);

        assertThatThrownBy(() -> productService.createProduct(productRequestDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Price must be greater than 0");

        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void shouldThrowExceptionWhenNameIsEmpty() {

        ProductRequestDto productRequestDto = new ProductRequestDto(
                "", "High quality phone", new BigDecimal(1100));

        assertThatThrownBy(() -> productService.createProduct(productRequestDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Product name must not be empty");

        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void shouldThrowExceptionWhenDescriptionIsNull() {

        ProductRequestDto productRequestDto = new ProductRequestDto(
                "Test Product", null, new BigDecimal(1100));

        assertThatThrownBy(() -> productService.createProduct(productRequestDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Description must not be null");

        verify(productRepository, never()).save(any(Product.class));
    }
}
