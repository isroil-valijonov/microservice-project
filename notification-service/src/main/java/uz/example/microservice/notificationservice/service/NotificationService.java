package uz.example.microservice.notificationservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import uz.example.microservice.orderservice.event.OrderPlacedEvent;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    @KafkaListener(topics = "order-placed", groupId = "notificationService")
    public void listen(OrderPlacedEvent orderPlacedEvent) {

        log.info("Got Message from the order-placed topic {}", orderPlacedEvent);

        log.info("Sending email to {}", orderPlacedEvent.getEmail());

    }
}
