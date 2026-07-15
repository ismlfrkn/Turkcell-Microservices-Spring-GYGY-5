package com.turkcell.product_service.consumer;

import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.turkcell.product_service.event.OrderCreatedEvent;

@Configuration
public class OrderCreatedConsumer {

    @Bean
    public Consumer<OrderCreatedEvent> orderCreatedEventConsumer() {
        return event -> {

            for (var item : event.orderItems()) {
                if (item.quantity() > 5) { //5 TANE KALMIŞ GİBİ DÜŞÜNELİM ŞUANLIK DB KONTROLÜ OLMADIĞI İÇİN
                    System.out.println("Ürün ID: " + item.productId() + " için sipariş tamamlandı.");
                } else {
                    System.out.println(
                            "Ürün ID: " + item.productId() + " için yetersiz stok nedeniyle sipariş iptal edildi.");
                }
            }

        };
    }
}
