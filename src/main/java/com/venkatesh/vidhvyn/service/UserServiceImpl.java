package com.venkatesh.vidhvyn.service;

import com.venkatesh.vidhvyn.DTO.RegisterDTO;
import com.venkatesh.vidhvyn.model.EmailVerificationToken;
import com.venkatesh.vidhvyn.model.Role;
import com.venkatesh.vidhvyn.model.User;
import com.venkatesh.vidhvyn.repository.EmailVerificationTokenRepository;
import com.venkatesh.vidhvyn.repository.RoleRepository;
import com.venkatesh.vidhvyn.repository.UserRepository;
import jakarta.persistence.Transient;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailVerificationTokenRepository emailVerificationTokenRepository;
    private final EmailService emailService;


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
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setDateOfBirth(registerDTO.getDateOfBirth());
        user.getRoles().add(defaultRole);
        userRepository.save(user);

        String token= UUID.randomUUID().toString();
        EmailVerificationToken emailVerificationToken = new EmailVerificationToken();
        emailVerificationToken.setToken(token);
        emailVerificationToken.setUser(user);
        emailVerificationToken.setExpiryDate(LocalDateTime.now().plusMinutes(15));
        emailVerificationTokenRepository.save(emailVerificationToken);

        String link="http://localhost:8080/user/verify-email?token="+token;
        emailService.sendVerificationEmail(user.getEmail(), link);

    }

    @Transactional
    @Override
    public boolean resendVerificationEmail(String email) {
        User user=userRepository.findByEmail(email).orElse(null);
        if(user==null)
            return false;
        if(user.isEnabled())
            return false;



        EmailVerificationToken emailVerificationToken=emailVerificationTokenRepository.findByUser(user)
                        .orElseGet(()->{
                           EmailVerificationToken newEmailVerificationToken=new EmailVerificationToken();
                           newEmailVerificationToken.setUser(user);
                           return newEmailVerificationToken;
                        });
        emailVerificationToken.setToken(UUID.randomUUID().toString());
        emailVerificationToken.setExpiryDate(LocalDateTime.now().plusMinutes(15));
        emailVerificationToken.setUsed(false);
        emailVerificationTokenRepository.save(emailVerificationToken);

        String link = "http://localhost:8080/user/verify-email?token=" + emailVerificationToken.getToken();
        emailService.sendVerificationEmail(user.getEmail(), link);
        return true;
    }
}
