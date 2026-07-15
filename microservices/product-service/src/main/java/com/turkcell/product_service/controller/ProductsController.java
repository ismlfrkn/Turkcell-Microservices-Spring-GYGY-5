package com.turkcell.product_service.controller;

import java.time.Instant;
import java.util.UUID;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.turkcell.product_service.entity.OutboxEvent;
import com.turkcell.product_service.entity.TestClass;
import com.turkcell.product_service.event.TestEvent;
import com.turkcell.product_service.repository.OutboxRepository;

@RequestMapping("/api/products")
@RestController
public class ProductsController {
    private final StreamBridge streamBridge;
    private final OutboxRepository outboxRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ProductsController(StreamBridge streamBridge, OutboxRepository outboxRepository) {
        this.streamBridge = streamBridge;
        this.outboxRepository = outboxRepository;
    }

    //Senkron iletişim için oluşturulan method
    @GetMapping("/test")
    public TestClass test2()
    {
        return new TestClass("Senkron iletişim tamamlandı. User-Service(Client) -> Product-Service");
    }


    @GetMapping
    public String test(@RequestParam String message)
    {
        UUID id = UUID.randomUUID();
        UUID eventId = UUID.randomUUID();
        var event = new TestEvent(eventId, message, UUID.randomUUID());
        //streamBridge.send("testEvent-out-0", event);

        //KAFKAYA bir event gidecekse, önce kayıt altına alınır, sonra Kafka'ya gönderilir. Bu sayede Kafka'ya gönderilemeyen eventler kaybolmaz.
        //Transactional outbox pattern: Eventler önce veritabanına kaydedilir, sonra Kafka'ya gönderilir. Eğer Kafka'ya gönderilemezse, eventler veritabanında kalır ve daha sonra tekrar gönderilebilir.
        //Outbox -> XEvent,XTarih, XTopic, XPayload

        //Daha sonra bir mekanizma bu kayıtları okuyacak ve kafkaya gönderecek.
        //Pooling (1) => Belirli aralıklarla veritabanına bakacak ve gönderilmemiş eventleri kafkaya gönderecek.
        //Her 20 snde => Select * from outbox where status = 'PENDING' and retry_count < 3 Bu bir performans kaybıdır.

        //Debezium (2) -> CDC (Change Data Capture) -> Outbox pattern
        
        OutboxEvent outboxEvent = new OutboxEvent();
        outboxEvent.setId(eventId);
        outboxEvent.setAggregateType("Product");
        outboxEvent.setAggregateId(id.toString());
        outboxEvent.setEventType("testEvent");
        outboxEvent.setPayload(toJson(event));
        // outboxEvent.setStatus(OutboxStatus.PENDING); // CDC (Debezium) ile artık kullanılmıyor
        outboxEvent.setCreatedAt(Instant.now());
        
        outboxRepository.save(outboxEvent);
        

        
        return "Başarılı";
    }

    private String toJson(Object o)
    {
        try{ return objectMapper.writeValueAsString(o);}
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }   
    }
}
