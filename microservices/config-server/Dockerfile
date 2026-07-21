# MULTI-STAGE build.
# Stage 1 (builder): JDK + Maven ile jar uretilir, bu katmanlar final imaja girmez.
# Stage 2 (runtime): sadece JRE + jar kalir -> imaj ~1.2GB yerine ~200MB.
#
# Build context yine microservices/ dizini olmali:
#   cd microservices
#   docker build -f config-server/Dockerfile -t config-server .

# ---------- Stage 1: builder ----------
FROM maven:3.9-eclipse-temurin-21 AS builder

WORKDIR /app

# Once pom'lari kopyala ki bagimlilik indirme katmani cache'lensin
COPY pom.xml ./pom.xml
COPY config-server/pom.xml ./config-server/pom.xml

RUN mvn -f config-server/pom.xml dependency:go-offline -B

# Kaynak kodu kopyala ve paketle
COPY config-server/src ./config-server/src

RUN mvn -f config-server/pom.xml clean package -DskipTests -B

# ---------- Stage 2: runtime ----------
# Yeni FROM ile sifirdan baslanir; onceki stage'den sadece istedigimizi ceker.
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Builder stage'inden SADECE jar'i al; Maven, kaynak kod, .m2 cache geride kalir.
COPY --from=builder /app/config-server/target/config-server-0.0.1-SNAPSHOT.jar app.jar

# application.yaml -> server.port: 8888
EXPOSE 8888

# entrypoint => container calistigi an olacak is, build ani degil.
ENTRYPOINT ["java", "-jar", "app.jar"]