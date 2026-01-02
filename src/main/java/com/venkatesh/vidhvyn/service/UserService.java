package com.venkatesh.vidhvyn.service;

import com.venkatesh.vidhvyn.DTO.RegisterDTO;

public interface UserService {
    void registerUser(RegisterDTO registerDTO);
    boolean resendVerificationEmail(String email);
}
