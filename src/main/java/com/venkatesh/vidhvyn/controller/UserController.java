package com.venkatesh.vidhvyn.controller;

import com.venkatesh.vidhvyn.DTO.RegisterDTO;
import com.venkatesh.vidhvyn.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/register")
    public String shoeRegister() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute RegisterDTO registerDTO, Model model) {
        try {
            userService.registerUser(registerDTO);
            model.addAttribute("message", "User registered successfully");
        } catch (RuntimeException e) {
            if ("Email already exists".equals(e.getMessage()))
                model.addAttribute("message", "Email already exists");
            else if ("Mobile number already exists".equals(e.getMessage()))
                model.addAttribute("message", "Mobile number already exists");
            else
                model.addAttribute("message", "Something went wrong");

        }
        return "redirect:/";
    }
}
