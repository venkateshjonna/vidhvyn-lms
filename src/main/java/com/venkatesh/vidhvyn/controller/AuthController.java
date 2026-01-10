package com.venkatesh.vidhvyn.controller;

import com.venkatesh.vidhvyn.DTO.LoginRequestDTO;
import com.venkatesh.vidhvyn.model.User;
import com.venkatesh.vidhvyn.service.AuthService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/login")
    public String showLogin(Model model) {
        model.addAttribute("loginDTO", new LoginRequestDTO());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute LoginRequestDTO loginRequestDTO, Model model, HttpSession session) {
        User user=authService.authenticate(loginRequestDTO);
        if(user==null)
        {
            model.addAttribute("error", "Invalid credentials or email not verified");
            model.addAttribute("loginDTO", loginRequestDTO);
            return "login";
        }
        session.setAttribute("LOGGED_USER", user.getId());
        return "redirect:/dashboard/main";
    }
}
