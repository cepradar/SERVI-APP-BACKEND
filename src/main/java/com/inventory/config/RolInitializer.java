package com.inventory.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.inventory.model.Rol;
import com.inventory.repository.RolesRepository;

@Component
@Order(10)
public class RolInitializer implements CommandLineRunner {

    @Autowired
    private RolesRepository roleRepository;

    @Override
    public void run(String... args) {
        if (roleRepository.findByName("ADMIN") == null) {
            roleRepository.save(new Rol("ADMIN", "#4f46e5", "Administrador del sistema"));
        }
        if (roleRepository.findByName("CLIENTE") == null) {
            roleRepository.save(new Rol("CLIENTE", "#2563eb", "Cliente con acceso basico"));
        }
        if (roleRepository.findByName("TECNICO") == null) {
            roleRepository.save(new Rol("TECNICO", "#16a34a", "Tecnico de servicio"));
        }
        if (roleRepository.findByName("USER") == null) {
            roleRepository.save(new Rol("USER", "#2563eb", "Usuario interno"));
        }
    }
}
