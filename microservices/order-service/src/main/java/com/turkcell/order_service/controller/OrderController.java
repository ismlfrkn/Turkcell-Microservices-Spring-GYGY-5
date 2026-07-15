package com.turkcell.order_service.controller;

import java.util.UUID;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.order_service.dto.request.OrderCreateRequest;
import com.turkcell.order_service.event.OrderCreatedEvent;
import com.turkcell.order_service.event.OrderItemEvent;

@RequestMapping("/api/orders")
@RestController
public class OrderController {

    private final StreamBridge streamBridge;

    public OrderController(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @PostMapping()
    public String createOrder(@RequestBody OrderCreateRequest request) {

        var order = new OrderCreatedEvent(UUID.randomUUID(), request.orderItems().stream()
                .map(item -> new OrderItemEvent(item.productId(), item.quantity()))
                .toList());
        streamBridge.send("orderEvent-out-0", order);
        return order.orderId() + " Numaralı sipariş oluşturuldu stok kontrolü gerçekleştiriliyor...";
    }
}
