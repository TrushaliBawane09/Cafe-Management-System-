Project Name -> Cafe-Operations-Management-System

Cafe Operations Management System is a microservices-based application designed to simplify and automate the daily operations of a cafe. It provides seamless management of categories, products, orders, billing, inventory, and customers. The system is built with Spring Boot microservices on the backend and an Angular frontend, offering a smooth and user-friendly experience.

Technology : Backend: Microservices, Spring Boot, Java 8, Hibernate Frontend: Angular Database: MySQL Authentication: JWT-based authentication & authorization

User Roles : Super Admin → Full control over system and configuration Admin / Staff → Manage categories, products, billing. order User / Customer → Place orders and view bills

Architecture: AUTH-SERVICE CATEGORY-SERVICE PRODUCT-SERVICE BILL-SERVICE EUREKA-SERVICE API-GATEWAY

Modules (Microservices) 🔑 AUTH-SERVICE User login, registration, and role-based access control Secured with JWT authentication

📂 CATEGORY-SERVICE Add, update, delete, and view categories

📦 PRODUCT-SERVICE Add, update, delete, and view products

BILL-SERVICE Place new orders and generate bills Delete or update existing bills View order and billing history

🔍 EUREKA-SERVICE Service discovery server All microservices are registered with Eureka

🌐 API-GATEWAY Centralized entry point for all client requests Routes requests to respective microservices securely
