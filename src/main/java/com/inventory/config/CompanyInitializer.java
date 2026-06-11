package com.inventory.config;

import com.inventory.model.Company;
import com.inventory.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Inicializa la información de la empresa al arrancar la aplicación
 */
@Slf4j
@Component
@Order(1)
@RequiredArgsConstructor
public class CompanyInitializer implements CommandLineRunner {

    private final CompanyRepository companyRepository;

    @Override
    @Transactional
    public void run(String... args) {
        log.info("🏢 Inicializando información de la empresa...");

        // Verificar si ya existe una empresa
        if (companyRepository.count() > 0) {
            log.info("✅ La empresa ya está registrada en la base de datos");
            return;
        }

        // Crear empresa con información por defecto
        Company company = new Company();
        company.setNit("900123456-7");
        company.setRazonSocial("SERVIPRADA");
        company.setDireccion("Calle 123 #45-67");
        company.setCodigoPostal("470001");
        company.setTelefono("+57 350 234 4185");
        company.setCorreo("contacto@washo.com.co");
        company.setSitioWeb("https://serviprada.com");
        company.setRepresentanteLegal("Administrador Principal");
        company.setNumeroRegimen("Régimen Simplificado 12345");

        companyRepository.save(company);
        
        log.info("✅ Empresa creada exitosamente:");
        log.info("   - NIT: {}", company.getNit());
        log.info("   - Razón Social: {}", company.getRazonSocial());
        log.info("   - Correo: {}", company.getCorreo());
        log.info("⚠️  Recuerda actualizar esta información desde el panel de administración");
    }
}
