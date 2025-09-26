package uz.example.microservice.orderservice.client;

import org.springframework.stereotype.Component;
import uz.example.microservice.orderservice.dto.UserDto;

@Component
public class UserClientFallback implements UserClient {
    @Override
    public UserDto getUserById(Long id) {
        return null;
    }
}
