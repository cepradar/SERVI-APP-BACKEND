package com.inventory.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.inventory.model.CategoryProduct;
import com.inventory.repository.CategoryProductRepository;

@Component
@Order(10)
public class CategoryProductInitializer implements CommandLineRunner {

    @Autowired
    private CategoryProductRepository categoryProductRepository;

    @Override
    public void run(String... args) {
        if (!categoryProductRepository.findById("N").isPresent()) {
            categoryProductRepository.save(new CategoryProduct("N", "REPUESTOS NUEVOS", "NUEVOS"));
        }
        if (!categoryProductRepository.findById("U").isPresent()) {
            categoryProductRepository.save(new CategoryProduct("U", "REPUESTOS USADOS", "USADOS"));
        }
    }
}
