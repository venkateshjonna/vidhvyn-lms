package com.venkatesh.vidhvyn.init;

import com.venkatesh.vidhvyn.model.Role;
import com.venkatesh.vidhvyn.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AdminAndRoleInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    @Override
    public void run(String... args) throws Exception {

        if(!roleRepository.existsByName("ROLE_ADMIN"))
        roleRepository.save(new Role("ROLE_ADMIN"));
        if(!roleRepository.existsByName("ROLE_STUDENT"))
            roleRepository.save(new Role("ROLE_STUDENT"));
    }
}
