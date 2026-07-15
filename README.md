# MİKROSERVİS HAKKINDA BİLGİLER

Ana dizinde aşağıdaki komutu kullanarak istediğiniz servisi çalıştırabilirsiniz:

```bash
mvn spring-boot:run -pl service-name
```

```
-pl:project list
```
---
## (1) Eureka (Discovery) Server

Eureka Server, mikroservislerin ağa kayıt olmasını ve birbirlerini keşfetmesini sağlayan bir servis keşif (Service Discovery) sunucusudur. Çalışan servis instance'larının adreslerini tutar ve istemcilere bu bilgileri sağlar.

Yük dengeleme (Load Balancing) işlemini doğrudan Eureka Server yapmaz. Eureka'dan alınan instance listesi, Spring Cloud LoadBalancer tarafından varsayılan olarak **Round Robin** algoritması kullanılarak dengeli bir şekilde dağıtılır. Bu algoritmada gelen istekler, servis instance'larına sırasıyla yönlendirilir.

---

## (2) Gateway Server

API Gateway, istemcilerden gelen tüm istekler için giriş noktası (Entry Point) olarak görev yapar. Gelen istekleri ilgili mikroservislere yönlendirir ve sistemdeki servislerin tek bir adres üzerinden erişilebilir olmasını sağlar.

Gateway, Eureka Server ile entegre çalışarak kayıtlı servis instance'larını keşfeder. Servis yönlendirmelerinde Spring Cloud LoadBalancer kullanılır ve varsayılan olarak **Round Robin** algoritması ile istekler servis instance'ları arasında sırayla dağıtılır. Böylece sistemde yük dengeli bir şekilde paylaşılır ve yüksek erişilebilirlik sağlanır.

---

## (3) Synchronous (Senkron) ve Asynchronous (Asenkron) İletişim

### (a) Synchronous (Senkron) İletişim

Senkron iletişimde bir servis, başka bir servise istek gönderir ve **cevap gelene kadar bekler**. Karşı servis yanıt verdikten sonra işlem kaldığı yerden devam eder.

#### Kullanım Alanları

- REST API
- OpenFeign
- gRPC

---

### (b) Asynchronous (Asenkron) İletişim

Asenkron iletişimde bir servis isteği veya olayı (event) gönderir ve **cevabı beklemeden işlemine devam eder**. Mesajlar genellikle bir **Message Broker** (Kafka, RabbitMQ vb.) üzerinden iletilir.

#### Kullanım Alanları

- Apache Kafka
- RabbitMQ
- ActiveMQ

