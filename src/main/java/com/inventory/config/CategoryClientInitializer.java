package com.inventory.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.inventory.model.CategoryClient;
import com.inventory.repository.CategoryClientRepository;

@Component
@Order(10)
public class CategoryClientInitializer implements CommandLineRunner {

    @Autowired
    private CategoryClientRepository categoryClientRepository;

    @Override
    public void run(String... args) {
        if (!categoryClientRepository.findById("PART").isPresent()) {
            categoryClientRepository.save(new CategoryClient("PART", "PARTICULARES", true));
        }
        if (!categoryClientRepository.findById("E").isPresent()) {
            categoryClientRepository.save(new CategoryClient("E", "EMPRESAS", true));
        }
    }
}
