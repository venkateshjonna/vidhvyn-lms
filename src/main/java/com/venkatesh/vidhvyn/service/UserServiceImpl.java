package com.venkatesh.vidhvyn.service;

import com.venkatesh.vidhvyn.DTO.RegisterDTO;
import com.venkatesh.vidhvyn.model.Role;
import com.venkatesh.vidhvyn.model.User;
import com.venkatesh.vidhvyn.repository.RoleRepository;
import com.venkatesh.vidhvyn.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public void registerUser(RegisterDTO registerDTO) {
        if(userRepository.existsByEmail(registerDTO.getEmail()))
        {
           throw new RuntimeException("Email already exists");
        }
        if(userRepository.existsByMobileNumber(registerDTO.getMobileNumber()))
        {

            throw new RuntimeException("Mobile number already exists");
        }
        Role defaultRole = roleRepository.findByName("ROLE_STUDENT")
                .orElseThrow(()->new RuntimeException("No role found"));
        User user = new User();
        user.setUsername(registerDTO.getEmail());
        user.setEmail(registerDTO.getEmail());
        user.setMobileNumber(registerDTO.getMobileNumber());
        user.setPassword(registerDTO.getPassword());
        user.setDateOfBirth(registerDTO.getDateOfBirth());
        user.getRoles().add(defaultRole);

        userRepository.save(user);
    }
}
