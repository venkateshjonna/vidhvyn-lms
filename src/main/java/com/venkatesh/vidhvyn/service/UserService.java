package com.venkatesh.vidhvyn.service;

import java.util.Optional;

import com.venkatesh.vidhvyn.DTO.RegisterDTO;
import com.venkatesh.vidhvyn.model.User;

public interface UserService {
    void registerUser(RegisterDTO registerDTO);

    boolean resendVerificationEmail(String email);

    Optional<User> findByEmail(String email);
}
