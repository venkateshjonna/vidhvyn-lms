package com.venkatesh.vidhvyn.controller;

import com.venkatesh.vidhvyn.DTO.RegisterDTO;
import com.venkatesh.vidhvyn.model.Role;
import com.venkatesh.vidhvyn.model.User;
import com.venkatesh.vidhvyn.repository.RoleRepository;
import com.venkatesh.vidhvyn.repository.UserRepository;
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

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @GetMapping("/register")
    public String shoeRegister()
    {
        return "register";
    }
    @PostMapping("/register")
    public String registerUser(@ModelAttribute RegisterDTO registerDTO, Model model)
    {
        if(userRepository.existsByEmail(registerDTO.getEmail()))
        {
            model.addAttribute("error", "This email already exists");
            return "register";
        }
        if(userRepository.existsByMobileNumber(registerDTO.getMobileNumber()))
        {
            model.addAttribute("error", "This mobile number already exists");
            return "register";
        }
        Role defaultRole = roleRepository.findByName("ROLE_STUDENT")
                        .orElseThrow(()->new RuntimeException("No role found"));
        System.out.println("DOB => " +registerDTO.getDateOfBirth());
        User user = new User();
        user.setUsername(registerDTO.getEmail());
        user.setEmail(registerDTO.getEmail());
        user.setMobileNumber(registerDTO.getMobileNumber());
        user.setPassword(registerDTO.getPassword());
        user.setDateOfBirth(registerDTO.getDateOfBirth());
        user.getRoles().add(defaultRole);

        userRepository.save(user);
        return "redirect:/";

    }

}
