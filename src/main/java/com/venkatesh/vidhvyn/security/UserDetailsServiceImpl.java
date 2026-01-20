package com.venkatesh.vidhvyn.security;

import com.venkatesh.vidhvyn.model.User;
import com.venkatesh.vidhvyn.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Spring Security calls this method internally
     * whenever it needs to authenticate a user
     */
    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        // 1️⃣ Fetch user from DB
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User not found with email: " + email));

        // 2️⃣ Block login if email not verified
        if (!user.isEnabled()) {
            throw new DisabledException("Email not verified");
        }

        // 3️⃣ Convert our User → Spring Security UserDetails
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(
                        user.getRoles().stream()
                                .map(role -> role.getName())
                                .toArray(String[]::new))
                .accountLocked(false)
                .disabled(!user.isEnabled())
                .build();
    }
}
