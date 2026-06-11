package com.inventory.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.inventory.model.Sede;
import com.inventory.repository.SedeRepository;

@Component
@Order(10)
public class SedeInitializer implements CommandLineRunner {

    @Autowired
    private SedeRepository sedeRepository;

    @Override
    public void run(String... args) {
        if (sedeRepository.findById("SP").isEmpty()) {
            sedeRepository.save(new Sede("SP", "SERVI PRADA"));
        }
    }
}
