package com.venkatesh.vidhvyn.service;

import com.venkatesh.vidhvyn.DTO.LoginRequestDTO;
import com.venkatesh.vidhvyn.model.User;
import com.venkatesh.vidhvyn.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User authenticate(LoginRequestDTO loginRequestDTO) {

        User user = userRepository.findByEmail(loginRequestDTO.getEmail())
                .orElse(null);

        if(user==null)
            return null;
        if(!user.isEnabled())
            return null;
        if(!passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword()))
            return null;

        return user;
    }
}
