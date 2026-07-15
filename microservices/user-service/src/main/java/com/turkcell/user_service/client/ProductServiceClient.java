package com.turkcell.user_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.turkcell.user_service.client.model.ProductTestResponse;

//UserService içinde ProductService isteklerini atacak yer.

//Service ismi eurekadaki ile aynı olmalı (isteği atıp ulaşmak istediğimiz server)
@FeignClient(name = "product-service")
public interface ProductServiceClient {

    @GetMapping("/api/products/test") //Yukarıdaki product-service içindeki adresin sonuna bunu ekle.
    ProductTestResponse test(); // Gelecek cevapta bu türde olacak. O yüzden model package içinde ProductTestResponse adında bir record oluşturduk. (record = sadece veri taşıyan class)
}
