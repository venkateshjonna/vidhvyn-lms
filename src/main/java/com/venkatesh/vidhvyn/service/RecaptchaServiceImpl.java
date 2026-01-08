package com.venkatesh.vidhvyn.service;

import com.venkatesh.vidhvyn.DTO.RecaptchaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class RecaptchaServiceImpl implements RecaptchaService {

    @Value("${recaptcha.secret-key}")
    private String secretKey;

    @Value("${recaptcha.verify-url}")
    private String verifyUrl;

    private final RestTemplate restTemplate=new RestTemplate();

    @Override
    public boolean verify(String recaptchaResponse) {
        String url = verifyUrl +
                "?secret=" + secretKey +
                "&response=" + recaptchaResponse;

        RecaptchaDTO response =
                restTemplate.postForObject(url, null, RecaptchaDTO.class);

        return response != null && response.isSuccess();
    }
}
