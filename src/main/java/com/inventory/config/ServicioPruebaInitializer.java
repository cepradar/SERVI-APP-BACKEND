package com.inventory.config;

import com.inventory.model.CategoriaElectrodomestico;
import com.inventory.model.Servicio;
import com.inventory.repository.CategoriaElectrodomesticoRepository;
import com.inventory.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Order(50)
public class ServicioPruebaInitializer implements CommandLineRunner {

    @Autowired private ServicioRepository servicioRepository;
    @Autowired private CategoriaElectrodomesticoRepository categoriaElectrodomesticoRepository;

    @Override
    public void run(String... args) throws Exception {
        if (!servicioRepository.existsByCodigo("SRV-PRUEBA-001")) {
            CategoriaElectrodomestico catElectro = categoriaElectrodomesticoRepository.findByNombre("Televisor").orElse(null);

            Servicio servicio = new Servicio(
                    "SRV-PRUEBA-001",
                    "Diagnóstico General de Prueba",
                    new BigDecimal("50000.00"),
                    "DIAGNOSTICO");
            servicio.setDescripcion("Servicio de diagnóstico general para pruebas del sistema");
            servicio.setDuracionEstimadaMinutos(60);
            servicio.setGarantiaDias(15);
            servicio.setCategoriaElectrodomestico(catElectro);
            servicio.setActivo(true);

            servicioRepository.save(servicio);
            System.out.println("✅ Servicio de prueba creado: SRV-PRUEBA-001");
        }

        if (!servicioRepository.existsByCodigo("SRV-PRUEBA-002")) {
            Servicio servicio2 = new Servicio(
                    "SRV-PRUEBA-002",
                    "Mantenimiento Preventivo de Prueba",
                    new BigDecimal("80000.00"),
                    "MANTENIMIENTO");
            servicio2.setDescripcion("Servicio de mantenimiento preventivo para pruebas del sistema");
            servicio2.setDuracionEstimadaMinutos(90);
            servicio2.setGarantiaDias(30);
            servicio2.setActivo(true);

            servicioRepository.save(servicio2);
            System.out.println("✅ Servicio de prueba creado: SRV-PRUEBA-002");
        }
    }
}
