package com.venkatesh.vidhvyn.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @GetMapping("/main")
    public String showDashboard(HttpSession session) {
        if (session.getAttribute("LOGGED_USER") == null) {
            return "redirect:/auth/login";
        }
        return "dashBoard";
    }
}
