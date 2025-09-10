package uz.example.microservice.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.example.microservice.orderservice.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
