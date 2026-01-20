🔐 JWT Authentication – Vidhvyn LMS

This branch implements JWT-based authentication for the Vidhvyn LMS backend using Spring Boot + Spring Security.

The goal is to move from session-based login to stateless, token-based authentication, suitable for frontend frameworks (React, Mobile Apps, etc.).

📌 Features Implemented

JWT token generation on login

Stateless authentication

Custom JwtAuthFilter

UserDetailsService integration

Secure password handling with BCrypt

Protected APIs using Spring Security

🧠 Architecture Overview
Client
  ↓ (email + password)
Auth API (/api/auth/login)
  ↓
JWT Token Generated
  ↓
Client stores token
  ↓
Client sends token in Authorization header
  ↓
JwtAuthFilter validates token
  ↓
Spring Security allows access

🔑 JWT Flow Explained
1️⃣ Login

User sends email & password

Credentials validated against DB

JWT token is generated and returned

2️⃣ Token Usage

Client sends token in every request:

Authorization: Bearer <JWT>

3️⃣ Token Validation

JwtAuthFilter:

extracts token

validates signature & expiry

extracts email

loads user via UserDetailsService

sets authentication in SecurityContext

🧩 Key Components
📁 JwtUtil

Generates JWT tokens

Validates tokens

Extracts claims (email, roles)

📁 UserDetailsServiceImpl

Loads user from DB

Checks email verification

Converts roles to Spring authorities

📁 JwtAuthFilter

Intercepts every request

Skips public APIs

Validates JWT

Authenticates user for Spring Security

📁 SecurityConfig

Configures public vs protected APIs

Registers JwtAuthFilter

Enables stateless authentication

🔓 Public APIs
/api/auth/login
/user/register
/user/verify-email
/

🔒 Protected APIs

All APIs except the above require a valid JWT token.

🧪 How to Test (Postman)
Login
POST /api/auth/login


Body:

{
  "email": "user@gmail.com",
  "password": "password"
}

Access Protected API
GET /dashboard/main


Headers:

Authorization: Bearer <JWT_TOKEN>

🛠 Tech Stack

Java 17

Spring Boot

Spring Security

JWT (jjwt 0.13.0)

Hibernate / JPA

MySQL

Postman (testing)

🚀 Next Improvements

Role-based authorization

JWT logout (token blacklist)

Refresh token mechanism

React frontend integration

👨‍💻 Author

Venkatesh Jonna
Backend Developer | Spring Boot | Security | JWT

🎯 FINAL STATUS

At this point you have:

✅ Proper JWT login
✅ Stateless backend
✅ Industry-level security flow
✅ Ready for frontend integration
