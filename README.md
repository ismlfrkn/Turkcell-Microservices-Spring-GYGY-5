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


## (4) Keycloak Kavramları

Keycloak, açık kaynak bir Identity and Access Management (IAM) / kimlik doğrulama-yetkilendirme sunucusudur (Red Hat tarafından geliştirilir). Amacı, her mikroservisin kendi login/şifre/rol yönetimini ayrı ayrı yazmak yerine, bu işi merkezi bir sunucuya devretmektir — yani Single Sign-On (SSO) sağlayan bir kimlik sağlayıcısı (identity provider).

### (a) Realm 
Keycloak içinde kullanıcıları, rolleri, client'ları (uygulamaları) ve authentication/authorization ayarlarını birbirinden izole eden bağımsız bir yönetim alanıdır. Farklı realm'ler tamamen ayrıdır — bir realm'deki kullanıcı, rol veya client tanımı başka bir realm'i hiç etkilemez, adeta Keycloak içinde çoklu-tenant (multi-tenant) bir yapı sağlar.

### (b) Realm Roles

Realm roles, bir realm genelinde tanımlanan ve o realm'e bağlı tüm client'lar arasında ortak kullanılabilen rollerdir (örn. USER, ADMIN, MODERATOR). Bir kullanıcıya realm role atadığında, o kullanıcı realm'deki hangi client'a giriş yaparsa yapsın bu rol token'ında (realm_access.roles claim'i altında) taşınır.


### (c) Users

Users, bir realm'e ait gerçek kullanıcı hesaplarının (username, email, ad-soyad, şifre gibi kimlik bilgileri) tutulduğu ve yönetildiği bölümdür. Bir kullanıcıya realm role veya client role atadığında, bu roller o kullanıcının Role Mapping sekmesinde eşleştirilir ve kullanıcı login olduğunda üretilen token'a (realm_access.roles veya resource_access.<client>.roles claim'i altında) yansıtılır.

### (d) Clients

Client, Keycloak'a kayıtlı olan ve kullanıcılar adına kimlik doğrulama/token talep eden her bir uygulamadır (ör. gateway-server, user-service). Bir kullanıcı login olduğunda, hangi Client üzerinden giriş yaptıysa token'a o Client'a özel bilgiler (client role'ler resource_access.<client-id>.roles altında) eklenir ve o uygulama bu token'ı kullanarak kullanıcıyı tanır.



