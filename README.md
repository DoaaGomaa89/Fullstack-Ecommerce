# Fullstack E-commerce

A modern full‑stack e‑commerce app with a web storefront, secure REST API, and relational database. Includes authentication, product catalog, cart, checkout, and order management.

---

# 🚀 Quick Start (Docker — Recommended)

Run the whole stack with Docker. **No local Java/Node/DB installs required.**

## Prerequisites
- [Docker](https://docs.docker.com/get-docker/) 20.10+
- [Docker Compose](https://docs.docker.com/compose/install/) v2+
- Git

## 1️⃣ Clone and Start

```bash
git clone https://github.com/DoaaGomaa89/Fullstack-Ecommerce.git
cd Fullstack-Ecommerce
docker compose up --build
```

> The first run may take a few minutes while images build and dependencies install.

## 2️⃣ Access

- **Frontend (React or Angular):**
  - React:  http://localhost:3000
  - Angular: http://localhost:4200
- **API / Swagger UI:** http://localhost:8080/swagger-ui.html
- **Database:**
  - PostgreSQL: localhost:5432
  - MySQL:     localhost:3306

> Exact ports depend on your `docker-compose.yml` mappings.

## 3️⃣ Stop / Reset

```bash
docker compose down                    # stop
docker compose down -v                 # stop + remove DB volume (clean slate)
docker compose up --build --no-cache   # force a clean rebuild
```

## 4️⃣ DB Shell (inside container)

```bash
# PostgreSQL
docker compose exec db psql -U $POSTGRES_USER -d $POSTGRES_DB

# MySQL
docker compose exec db mysql -u$MYSQL_USER -p$MYSQL_PASSWORD $MYSQL_DATABASE
```

---

# Tech stack
- **Front‑end:** React 18 + TypeScript (or Angular) with Router & Axios/HttpClient, Ant Design / Material UI
- **Back‑end:** Spring Boot 3 (Java 17), Spring Security (JWT), JPA/Hibernate
- **Database:** PostgreSQL 15 **or** MySQL 8
- **Docs:** OpenAPI/Swagger UI

---

# Dependencies

## Front end
- React (or Angular)
- React Router / Angular Router
- Axios / HttpClient
- TypeScript

## Back end
- `spring-boot-starter-web`, `spring-boot-starter-data-jpa`
- `spring-boot-starter-security` (JWT)
- DB driver (`org.postgresql:postgresql` or `mysql:mysql-connector-java`)
- JJWT (`io.jsonwebtoken:jjwt-*`) for JSON Web Tokens
- Springdoc OpenAPI UI (`springdoc-openapi-starter-webmvc-ui`)
- Lombok (optional)

---

# Configuration

Set environment variables via a `.env` at repo root or export in your shell. Spring Boot and Compose will consume them.

```bash
# --- Choose ONE database family ---

# PostgreSQL (common default)
SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/ecommerce_db
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=postgres

# MySQL (if compose uses mysql:8)
# SPRING_DATASOURCE_URL=jdbc:mysql://db:3306/ecommerce_db?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC
# SPRING_DATASOURCE_USERNAME=app
# SPRING_DATASOURCE_PASSWORD=app

# JPA/Hibernate
SPRING_JPA_HIBERNATE_DDL_AUTO=update

# JWT (HS512 needs ≥ 64‑byte secret)
JWT_SECRET=please_generate_a_long_random_secret_string_64chars_or_more

# CORS (dev origins)
CORS_ORIGINS=http://localhost:3000,http://127.0.0.1:3000,http://localhost:4200
```

---

# 🧑‍💻 Manual Setup (Local Dev)

Prefer running without Docker?

## Install prerequisites
- Java 17 + Maven
- Node.js 18+ + npm
- PostgreSQL 15 or MySQL 8

## Clone
```bash
git clone https://github.com/DoaaGomaa89/Fullstack-Ecommerce.git
cd Fullstack-Ecommerce
```

## Back end (Spring Boot)
```bash
cd backend
mvn -DskipTests package
java -jar target/*.jar
# API http://localhost:8080
# Swagger http://localhost:8080/swagger-ui.html
```
> Dev mode:
```bash
mvn spring-boot:run
```

## Front end
```bash
cd frontend
npm ci
# React:
npm start              # http://localhost:3000
# Angular:
# ng serve --open      # http://localhost:4200
```

---

# Database schema

- JPA can create/update schema on first run (`SPRING_JPA_HIBERNATE_DDL_AUTO=update`).
- Optional seed data can go in `src/main/resources/data.sql`.

**Manual bootstrap (PostgreSQL):**
```sql
CREATE DATABASE ecommerce_db;
CREATE USER ecommerce_user WITH ENCRYPTED PASSWORD 'YOUR_STRONG_PASSWORD';
GRANT ALL PRIVILEGES ON DATABASE ecommerce_db TO ecommerce_user;
```

**Manual bootstrap (MySQL):**
```sql
CREATE DATABASE ecommerce_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'ecommerce_user'@'%' IDENTIFIED BY 'YOUR_STRONG_PASSWORD';
GRANT ALL PRIVILEGES ON ecommerce_db.* TO 'ecommerce_user'@'%';
FLUSH PRIVILEGES;
```

---

# Project Structure (key parts)

```
backend/
├── src/main/java/.../ecommerce
│   ├── entity/           # Product, Category, User, Order, OrderItem, Payment, ...
│   ├── repository/       # Spring Data repositories
│   ├── controller/       # REST endpoints
│   ├── service/          # Business logic
│   ├── security/         # JWT filters/config
│   └── config/           # CORS, Swagger, etc.
├── src/main/resources/
│   ├── application.yml
│   └── data.sql          # optional seed data
└── Dockerfile

frontend/
├── src/
├── package.json
└── Dockerfile

docker-compose.yml
```

---

# Features

- 🔐 **JWT Authentication** (register/login) with role‑based access
- 🛍️ **Product Catalog**: categories, search, sort, filter
- 🛒 **Cart & Checkout**
- 📦 **Orders & Payments**
- 🧑‍💼 **Admin Dashboard**: manage products, categories, orders
- 📄 **OpenAPI Docs** via Swagger UI

---

# API Documentation

- Swagger UI at `http://localhost:8080/swagger-ui.html`
- Machine‑readable spec: `/v3/api-docs` (JSON)

---

# Troubleshooting

- **JWT: “key size not secure enough for HS512”**  
  Use a secret **≥ 64 bytes** in `JWT_SECRET`.

- **CORS 403 / “Invalid CORS request”**  
  Include your frontend origin in `CORS_ORIGINS` and backend CORS config.

- **Ports in use (3000/4200/8080/5432/3306)**  
  Edit mappings in `docker-compose.yml`, e.g.:
  ```yaml
  services:
    frontend:
      ports: ["3001:3000"]       # or ["4201:4200"] for Angular
    backend:
      ports: ["8081:8080"]
    db:
      ports: ["5433:5432"]       # Postgres
      # or ["3307:3306"]         # MySQL
  ```

- **DB connectivity**  
  ```bash
  docker compose ps
  docker compose logs db
  # Postgres
  docker compose exec db psql -U $POSTGRES_USER -d $POSTGRES_DB -c "\dt"
  # MySQL
  docker compose exec db mysql -u$MYSQL_USER -p$MYSQL_PASSWORD -e "SHOW TABLES" $MYSQL_DATABASE
  ```

- **Clean rebuild**  
  ```bash
  docker compose down -v
  docker compose up --build --no-cache
  ```

--- 
