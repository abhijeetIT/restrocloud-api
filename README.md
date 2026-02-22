# 🍽️ RestroCloud — Multi-Tenant Restaurant Management REST API

> A **Spring Boot + MySQL** based Multi-Restaurant Management System designed to automate restaurant operations including tables, menus, orders, billing, and staff management.  
> Built with clean architecture, scalability, and real-world SaaS concepts in mind. 🚀

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/SpringBoot-3.x-brightgreen)
![MySQL](https://img.shields.io/badge/Database-MySQL-blue)
![Hibernate](https://img.shields.io/badge/ORM-Hibernate-red)
![JWT](https://img.shields.io/badge/Auth-JWT-yellow)
![License](https://img.shields.io/github/license/abhijeetIT/restrocloud-api)

---

## 🧠 Overview

**RestroCloud** is a cloud-ready, multi-tenant restaurant management backend system where multiple restaurants can register and manage their own operations securely.

Each restaurant gets:
- ✅ Separate login
- ✅ Independent tables
- ✅ Custom menu
- ✅ Order tracking
- ✅ Billing management

This project demonstrates advanced backend concepts like:
- Multi-tenancy
- Role-based authentication
- Entity relationships
- Clean layered architecture
- DTO pattern implementation

---

## ⚙️ Core Features

### 🏢 Restaurant Management
- Restaurant Registration & Login
- Multi-tenant data isolation
- Admin & Staff role management

### 🍽️ Table Management
- Add / Update / Delete tables
- Table status tracking (`Available` / `Occupied`)

### 📋 Menu Management
- Add categories
- Add menu items
- Update price & availability

### 🛒 Order Management
- Create new orders
- Add multiple items to an order
- Automatic total calculation
- Order status updates:
    - `PENDING`
    - `PREPARING`
    - `COMPLETED`
    - `CANCELLED`

### 💳 Billing & Payments
- Generate invoice
- Mark as `PAID` / `UNPAID`
- Payment method tracking

---

## 🏗️ Architecture

RestroCloud follows a clean **layered architecture** pattern:

```
Controller → Service → Repository → Entity → Database
```

### Design Principles Used
- Dependency Injection
- Loose Coupling
- SOLID Principles
- DTO Pattern
- Global Exception Handling

---

## 🛠️ Tech Stack

| Layer | Technology |
|:------|:-----------|
| Backend Framework | Spring Boot (v3.x) |
| Database | MySQL |
| ORM | Hibernate (Spring Data JPA) |
| Security | Spring Security + JWT |
| Architecture | Layered MVC + DTO |
| Build Tool | Maven |

---

## 📁 Project Structure

```
restrocloud-api/
├── src/
│   └── main/
│       ├── java/com/restrocloud/
│       │   ├── controller/       # REST Controllers
│       │   ├── service/          # Business Logic
│       │   ├── repository/       # Spring Data JPA Repos
│       │   ├── entity/           # JPA Entities
│       │   ├── dto/              # Data Transfer Objects
│       │   ├── security/         # JWT + Spring Security
│       │   └── exception/        # Global Exception Handling
│       └── resources/
│           └── application.properties
└── pom.xml
```

---

## 🗂️ Core Entities

| Entity | Description |
|:-------|:------------|
| `Restaurant` | Tenant root entity |
| `User` | Admin / Staff per restaurant |
| `Table` | Dining tables per restaurant |
| `MenuCategory` | Menu groupings |
| `MenuItem` | Individual menu items |
| `Order` | Customer orders |
| `OrderItem` | Items within an order |
| `Payment` | Billing & payment records |

> Each entity contains a `restaurant_id` field to ensure proper **multi-tenant isolation**.

---

## 🔐 Authentication & Authorization

- JWT-based stateless authentication
- Role-based access control:
    - `ADMIN` — Full access
    - `STAFF` — Limited operational access
- Secured API endpoints via Spring Security filters
- Custom exception handling using `@ControllerAdvice`

---

## 🌐 API Endpoints Overview
  commig

## 🚀 Getting Started

### 1️⃣ Clone Repository

```bash
git clone https://github.com/abhijeetIT/restrocloud-api.git
cd restrocloud-api
```

### 2️⃣ Configure Database

Update `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/restrocloud
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### 3️⃣ Run Application

```bash
mvn clean install
mvn spring-boot:run
```

Server runs on: **http://localhost:8080**

---

## 🚀 Future Enhancements

- [ ] Sales Analytics Dashboard
- [ ] Inventory Management
- [ ] Report Generation (Daily / Monthly)
- [ ] Docker Deployment
- [ ] Swagger / OpenAPI Documentation
- [ ] Cloud Deployment (AWS / Render / Koyeb)

---

## 🎯 Why This Project Is Resume-Worthy

| ✔ | Skill Demonstrated |
|:--|:-------------------|
| ✔ | SaaS-based multi-tenant architecture |
| ✔ | Professional JWT authentication |
| ✔ | Complex entity relationships |
| ✔ | Industry-level layered backend structure |
| ✔ | Role-based access control |
| ✔ | Clean DTO pattern implementation |

---

## 🧑‍💻 Developer

**Abhijeet Jha**  
📚 BCA Student | 💡 Backend Developer Enthusiast  
*Java & Spring Boot | REST APIs | System Design*

---

## 🌐 Connect With Me

[![Email](https://img.shields.io/badge/Email-abhijeetj4324%40gmail.com-red?style=flat&logo=gmail)](mailto:abhijeetj4324@gmail.com)
[![LinkedIn](https://img.shields.io/badge/LinkedIn-abhijeet--jha19-blue?style=flat&logo=linkedin)](https://www.linkedin.com/in/abhijeet-jha19)
[![GitHub](https://img.shields.io/badge/GitHub-abhijeetIT-black?style=flat&logo=github)](https://github.com/abhijeetIT)
[![Instagram](https://img.shields.io/badge/Instagram-abhijeet__jha-purple?style=flat&logo=instagram)](https://www.instagram.com/abhijeet_jha)

---

> ⭐ If you found this project helpful or inspiring, consider giving it a **star** on GitHub!
