package com.venkatesh.vidhvyn.service;


import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSenderImpl mailSender;

    @Override
    public void sendVerificationEmail(String toMail, String link) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toMail);
        message.setSubject("Verification Email");
        message.setText("Your verification link is: " + link);
        mailSender.send(message);
    }
}
