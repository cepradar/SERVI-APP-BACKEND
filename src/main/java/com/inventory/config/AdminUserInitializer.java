package com.inventory.config;

import com.inventory.model.Rol;
import com.inventory.model.User;
import com.inventory.repository.RolesRepository;
import com.inventory.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Order(20)
public class AdminUserInitializer implements CommandLineRunner {

    private static final String ADMIN_USERNAME = "ADMIN";
    private static final String ADMIN_PASSWORD = "CANEYA";

    @Autowired private UserRepository userRepository;
    @Autowired private RolesRepository rolesRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.existsById(ADMIN_USERNAME)) {
            return;
        }

        Rol adminRole = rolesRepository.findByName("ADMIN");
        if (adminRole == null) {
            adminRole = rolesRepository.save(new Rol("ADMIN", "#4f46e5", "Administrador del sistema"));
        }

        User admin = new User();
        admin.setUsername(ADMIN_USERNAME);
        admin.setPassword(passwordEncoder.encode(ADMIN_PASSWORD));
        admin.setRole(adminRole);
        admin.setFirstName("Admin");
        admin.setLastName("Inicial");
        admin.setEmail("admin@local");

        userRepository.save(admin);
    }
}
