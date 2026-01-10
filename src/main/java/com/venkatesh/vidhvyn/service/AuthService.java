package com.venkatesh.vidhvyn.service;

import com.venkatesh.vidhvyn.DTO.LoginRequestDTO;
import com.venkatesh.vidhvyn.model.User;

public interface AuthService {
    User authenticate(LoginRequestDTO loginRequestDTO);
}
