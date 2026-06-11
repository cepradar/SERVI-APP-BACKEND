package com.inventory.config;

import com.inventory.model.CategoryProduct;
import com.inventory.model.Product;
import com.inventory.repository.CategoryProductRepository;
import com.inventory.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(50)
public class ProductoPruebaInitializer implements CommandLineRunner {

    @Autowired private ProductRepository productRepository;
    @Autowired private CategoryProductRepository categoryProductRepository;

    private static final String PRODUCTO_ID = "PROD-PRUEBA-001";

    @Override
    public void run(String... args) throws Exception {
        if (productRepository.existsById(PRODUCTO_ID)) {
            return;
        }

        CategoryProduct categoria = categoryProductRepository.findById("N").orElse(null);
        if (categoria == null) {
            System.out.println("⚠️  ProductoPruebaInitializer: categoría 'N' no encontrada, omitiendo.");
            return;
        }

        Product producto = new Product("Control Remoto Universal", 25000.0, 10, categoria);
        producto.setId(PRODUCTO_ID);
        producto.setDescription("Control remoto universal compatible con múltiples marcas de televisores");
        producto.setActivo(true);

        productRepository.save(producto);
        System.out.println("✅ Producto de prueba creado: " + PRODUCTO_ID);
    }
}
