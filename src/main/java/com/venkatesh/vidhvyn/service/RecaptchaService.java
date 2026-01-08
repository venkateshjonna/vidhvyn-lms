package com.venkatesh.vidhvyn.service;

public interface RecaptchaService {
    boolean verify(String recaptchaResponse);
}
