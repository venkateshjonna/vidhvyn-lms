package com.venkatesh.vidhvyn.service;

public interface EmailService {
    void sendVerificationEmail(String toMail,String link);
}
