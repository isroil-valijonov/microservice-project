package uz.example.microservice.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import uz.example.microservice.orderservice.dto.UserDto;

@FeignClient(
        name = "user-service",
        url = "${user-service.url}",
        fallback = UserClientFallback.class
)
public interface UserClient {

    @GetMapping("/api/users/{id}")
    UserDto getUserById(@PathVariable("id") Long id);
}

