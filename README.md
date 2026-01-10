🔐 Login Module – Session Based Authentication (Login Branch)

This branch implements user login functionality using Spring Boot, Thymeleaf, and Spring Security, with session-based authentication.
It serves as a foundation step before migrating to JWT-based authentication.

📌 Features Implemented

✅ Login using email & password

✅ Password encryption using BCrypt

✅ Email verification check before login

✅ Session-based authentication (HttpSession)

✅ Protected dashboard access

✅ Proper error handling on invalid login

✅ Spring Security configuration (basic)

🧠 Authentication Flow
1️⃣ Login Page (GET /auth/login)

Displays login form

Binds form data using LoginRequestDTO

model.addAttribute("loginDTO", new LoginRequestDTO());

2️⃣ User Submits Login Form (POST /auth/login)

Form fields:

email

password

Spring automatically maps these fields to LoginRequestDTO using:

@ModelAttribute LoginRequestDTO loginRequestDTO

3️⃣ Backend Authentication Logic
User user = authService.authenticate(loginRequestDTO);


Validation steps:

User exists in DB

Email is verified (isEnabled)

Password matches (BCrypt)

If any check fails → login page reloads with error message.

4️⃣ Session Creation on Successful Login
session.setAttribute("LOGGED_USER", user.getId());


This means:

The user is now considered logged in

Session data is stored on the server

Browser receives a session cookie (JSESSIONID)

5️⃣ Dashboard Access
@GetMapping("/dashboard/main")
public String showDashboard(HttpSession session) {
    if (session.getAttribute("LOGGED_USER") == null) {
        return "redirect:/auth/login";
    }
    return "dashBoard";
}


✔ Only logged-in users can access the dashboard
❌ Unauthenticated users are redirected to login

🧩 DTOs Used
LoginRequestDTO
public class LoginRequestDTO {
    @NotBlank @Email
    private String email;

    @NotBlank
    private String password;
}


Used for:

Binding form data

Clean separation of request data

Validation readiness

🔒 Security Configuration
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(
                "/",
                "/auth/login",
                "/user/register",
                "/dashboard/main"
            ).permitAll()
            .anyRequest().authenticated()
        );
    return http.build();
}

Current Behavior:

Login & dashboard routes are accessible

Security is minimal (manual session checks used)

JWT-based security will replace this

⚠️ Current Limitations (Intentional)

❌ Uses server-side sessions

❌ Manual authentication checks in controllers

❌ Not stateless

❌ Not suitable for APIs / mobile apps

These are intentional, as this branch focuses on basic login correctness.

🚀 Next Step (Planned)
🔐 JWT-Based Authentication

Upcoming improvements:

Stateless authentication using JWT

Token-based login (like Postman project)

Authorization: Bearer <token>

Custom JWT filter

Role-based access control

Removal of HttpSession

👉 This login branch serves as a baseline before JWT migration.

🧪 How to Test

Register a user

Verify email

Login with correct credentials

Access dashboard

Try invalid credentials → error message shown

Try dashboard without login → redirected to login

📁 Branch Purpose

Branch Name: login
Goal:

Implement and verify traditional session-based login before upgrading to JWT authentication.
