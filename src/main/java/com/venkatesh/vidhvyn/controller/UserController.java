package com.venkatesh.vidhvyn.controller;

import com.venkatesh.vidhvyn.DTO.RegisterDTO;
import com.venkatesh.vidhvyn.model.EmailVerificationToken;
import com.venkatesh.vidhvyn.model.User;
import com.venkatesh.vidhvyn.repository.EmailVerificationTokenRepository;
import com.venkatesh.vidhvyn.repository.UserRepository;
import com.venkatesh.vidhvyn.service.RecaptchaService;
import com.venkatesh.vidhvyn.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final EmailVerificationTokenRepository emailVerificationTokenRepository;
    private final RecaptchaService recaptchaService;
    @Value("${recaptcha.site-key}")
    private String recaptchaSiteKey;


    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("recaptchaSiteKey", recaptchaSiteKey);
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute RegisterDTO registerDTO,
                               @RequestParam("g-recaptcha-response") String captchaResponse,
                               Model model) {

        if (!recaptchaService.verify(captchaResponse)) {
            model.addAttribute("message", "Captcha verification failed. Please try again.");
            model.addAttribute("registerDTO", registerDTO);
            model.addAttribute("recaptchaSiteKey", recaptchaSiteKey);
            return "register";
        }

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
        return "register";
    }

    @GetMapping("/verification-success")
    public String verifySuccess()
    {
        return "verification-success";
    }
    @GetMapping("/verification-failure")
    public String verifyFailure()
    {
        return "verification-failure";
    }

    @GetMapping("/verify-email")
    public String verifyEmail(@RequestParam String token, Model model) {
        EmailVerificationToken emailVerificationToken=emailVerificationTokenRepository.findByToken(token)
                .orElse(null);
        if(emailVerificationToken==null)
            return "redirect:/user/verification-failure";
        if(emailVerificationToken.isUsed())
            return "redirect:/user/verification-failure";
        if(emailVerificationToken.getExpiryDate().isBefore(LocalDateTime.now()))
            return "redirect:/user/verification-failure";

        User user=emailVerificationToken.getUser();
        user.setEnabled(true);
        userRepository.save(user);

        emailVerificationToken.setUsed(true);
        emailVerificationTokenRepository.save(emailVerificationToken);

        return "redirect:/user/verification-success";
    }

    @GetMapping("/resend-verification")
    public String resendVerification() {
        return "resend-verification";
    }

    @PostMapping("/resend-verification")
    public String resendVerification(@RequestParam String email, Model model) {
        boolean sent=userService.resendVerificationEmail(email);
        if(sent)
            model.addAttribute("message", "Verification email has been sent");
        else
            model.addAttribute("message", "Something went wrong");
        return "resend-verification";
    }
}
