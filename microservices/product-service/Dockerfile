# MULTI-STAGE build.
# Stage 1 (builder): JDK + Maven ile jar uretilir, bu katmanlar final imaja girmez.
# Stage 2 (runtime): sadece JRE + jar kalir -> imaj ~1.2GB yerine ~200MB.
#
# Build context yine microservices/ dizini olmali:
#   cd microservices
#   docker build -f product-service/Dockerfile -t product-service .

# ---------- Stage 1: builder ----------
FROM maven:3.9-eclipse-temurin-21 AS builder

WORKDIR /app

# Once pom'lari kopyala ki bagimlilik indirme katmani cache'lensin
COPY pom.xml ./pom.xml
COPY product-service/pom.xml ./product-service/pom.xml

RUN mvn -f product-service/pom.xml dependency:go-offline -B

# Kaynak kodu kopyala ve paketle
COPY product-service/src ./product-service/src

RUN mvn -f product-service/pom.xml clean package -DskipTests -B

# ---------- Stage 2: runtime ----------
# Yeni FROM ile sifirdan baslanir; onceki stage'den sadece istedigimizi ceker.
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Builder stage'inden SADECE jar'i al; Maven, kaynak kod, .m2 cache geride kalir.
COPY --from=builder /app/product-service/target/product-service-0.0.1-SNAPSHOT.jar app.jar

# application.yaml -> server.port: 8082
EXPOSE 8082

# entrypoint => container calistigi an olacak is, build ani degil.
ENTRYPOINT ["java", "-jar", "app.jar"]