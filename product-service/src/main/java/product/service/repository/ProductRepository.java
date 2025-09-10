package product.service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import product.service.entity.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
}
