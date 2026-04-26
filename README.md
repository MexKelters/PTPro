# PTPro - Personal Training Pro

Backend REST API gebouwd met Spring Boot voor het beheren van personal trainers, sessies, boekingen en trainingsschema's.

---

## 1. Inleiding

PTPro verbindt personal trainers met hun klanten. Gebruikers kunnen een account aanmaken, trainers opzoeken en sessies boeken. Trainers kunnen sessies aanmaken en trainingsschema's uploaden. Admins beheren het gehele platform.

---

## 2. Beschrijving van de web-API en functionaliteit

De API draait op `http://localhost:8080`. Alle endpoints beginnen met `/api/v1/`.

De applicatie biedt de volgende functionaliteiten: gebruikersbeheer, trainerbeheer, sessies aanmaken en boeken, boekingen beheren en trainingsschema's uploaden en downloaden.

De API is beveiligd met OAuth2 JWT-tokens via Keycloak. Endpoints zijn opgedeeld in vier autorisatieniveaus: openbaar, USER, TRAINER en ADMIN. Het token stuur je mee als Bearer token:

```
Authorization: Bearer <jouw_access_token>
```

---

## 3. Overzicht van projectstructuur en gebruikte technieken

```
src/
├── main/
│   ├── java/com/ptpro/
│   │   ├── controller/
│   │   ├── service/
│   │   ├── repository/
│   │   ├── model/
│   │   ├── dto/
│   │   ├── mapper/
│   │   ├── exception/
│   │   └── config/
│   └── resources/
│       ├── application.properties
│       └── data.sql
└── test/
    ├── java/com/ptpro/
    │   ├── service/
    │   └── controller/
    └── resources/
        └── application-test.properties
```

Gebruikte technieken: Java 24, Spring Boot 3.4.13, Spring Security + OAuth2, Keycloak, PostgreSQL, Hibernate/JPA, MapStruct 1.6.3, Jakarta Validation, JUnit 5, Mockito, MockMvc en H2 (tests).

---

## 4. Benodigdheden

- Java 24
- Maven 3.8+
- PostgreSQL
- Keycloak 24+
- Postman 

---

## 5. Installatie instructies

**Stap 1 — Repository klonen**

```bash
git clone https://github.com/<jouw-gebruikersnaam>/PtPro.git
cd PtPro
```

**Stap 2 — Database aanmaken**

```sql
CREATE DATABASE ptpro;
```

**Stap 3 — Keycloak starten**

```bash
bin/kc.sh start-dev --http-port=9090
```

Maak daarna in de Keycloak admin console (`http://localhost:9090`) een realm aan met de naam `PtPro`, een client `PtPro` en de rollen `ROLE_USER`, `ROLE_TRAINER` en `ROLE_ADMIN`.

**Stap 4 — application.properties instellen**

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/ptpro
spring.datasource.username=ptpro_user
spring.datasource.password=jouwwachtwoord

spring.security.oauth2.resourceserver.jwt.issuer-uri=http://localhost:9090/realms/PtPro
spring.security.oauth2.resourceserver.jwt.jwk-set-uri=http://localhost:9090/realms/PtPro/protocol/openid-connect/certs
spring.security.oauth2.resourceserver.jwt.audiences=PtPro
```

**Stap 5 — Applicatie starten**

```bash
mvn spring-boot:run -Dspring-boot.run.jvmArguments="--enable-preview"
```

---

## 6. Uitleg over het uitvoeren van tests

Unit tests (geen database of Keycloak nodig):

```bash
mvn test -Dtest=UserServiceTest
mvn test -Dtest=TrainerServiceTest
```

Integratietests (gebruiken H2 in-memory database):

```bash
mvn test -Dtest=UserControllerIntegrationTest
mvn test -Dtest=TrainerControllerIntegrationTest
```

Alle tests tegelijk:

```bash
mvn verify
```

---

## 7. Testgebruikers en user-rollen

De testgebruikers staan in `src/main/resources/data.sql` en worden automatisch ingeladen bij opstarten.

| Naam | Email | Rol |
|---|---|---|
| John Doe | john@example.com | ROLE_USER |
| Jane Smith | jane@example.com | ROLE_USER |
| Mike Trainer | mike@example.com | ROLE_TRAINER |
| Sarah Coach | sarah@example.com | ROLE_TRAINER |
| Admin User | admin@example.com | ROLE_ADMIN |

Deze gebruikers moeten ook in Keycloak aangemaakt worden met de bijbehorende rol.

---

## 8. Overige commando's

Bouwen zonder tests:

```bash
mvn clean package -DskipTests
```

JAR uitvoeren:

```bash
java --enable-preview -jar target/PTPro-0.0.1-SNAPSHOT.jar
```

Keycloak token ophalen:

```bash
POST http://localhost:9090/realms/PtPro/protocol/openid-connect/token

grant_type=password
client_id=PtPro
username=<gebruikersnaam>
password=<wachtwoord>
```