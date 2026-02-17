# ✈️ Airport Ride Pooling Backend System

Backend Engineer Assignment – Smart Ride Pooling System  
Developed using Spring Boot, PostgreSQL, JWT Security.

---

# 🚀 Features

• User Register & Login with JWT Authentication  
• Role-based Security (USER / ADMIN)  
• Admin-only Ride Creation  
• Ride Booking with Seat & Luggage Constraints  
• Booking Cancellation  
• Smart Ride Pooling Algorithm  
• Dynamic Pricing Service  
• Concurrency-safe Booking using Pessimistic Locking  
• Ride Search with Pagination & Sorting  
• Swagger API Documentation  

---

# 🧠 Pooling Algorithm (DSA)

Steps:
1. Fetch active bookings
2. Cluster bookings by pickup location
3. Select cluster with max passengers
4. Validate seat & luggage limits
5. Estimate detour distance

Time Complexity → O(n log n)  
Space Complexity → O(n)

---

# ⚙️ Concurrency Handling

To prevent overbooking:

• Used Pessimistic DB Locking  
• Booking inside transaction  
• Concurrently tested using multi-thread test API  

This guarantees seat count consistency.

---

# 🏗️ High Level Architecture

Client → Security → Controller → Service → Repository → PostgreSQL  

Security handled using JWT filter before controllers.

Controllers handle API  
Services handle business logic  
Repositories access database  

---

# 🧩 Low Level Design

Entities:

User  
Ride  
Booking  
PoolGroup  

Relationships:

User 1 → * Booking  
Ride 1 → * Booking  
Ride 1 → 1 PoolGroup  

Patterns Used:

• Service Layer Pattern  
• Repository Pattern  
• DTO Pattern  
• Builder Pattern  

---

# 📊 Performance Design

• DB indexes on airport & departureTime  
• Pagination for search  
• Stateless JWT authentication  
• Locking for concurrency  

Supports high concurrent booking scenarios.

---

# 🧪 API Testing

Swagger UI → http://localhost:8080/swagger-ui.html

Steps:

1. Register user → /api/auth/register  
2. Login → /api/auth/login  
3. Authorize using JWT  
4. Create Ride (Admin)  
5. Book Ride  
6. Pool Ride  
7. Pricing API  

---

# 🛠️ Tech Stack

Java 17  
Spring Boot  
Spring Security + JWT  
Hibernate JPA  
PostgreSQL  
Swagger OpenAPI  

---

# ▶️ Run Project

mvn clean install  
mvn spring-boot:run  

---

# 📁 Project Structure

auth → Login/Register  
ride → Ride logic  
pool → Pooling algorithm  
pricing → Pricing service  
repository → DB layer  
security → JWT filter  

---

# 👨‍💻 Author

Nischay Kaushik  
Backend Engineer Assignment Submission
