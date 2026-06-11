package com.inventory.config;

import com.inventory.model.CategoriaEvento;
import com.inventory.model.Evento;
import com.inventory.repository.CategoriaEventoRepository;
import com.inventory.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Garantiza que los TipoEvento clave de Órdenes de Servicio existan.
 * Corre después de TipoEventoInitializer (@Order(30)), por lo que la categoría ORDEN
 * siempre estará disponible.
 */
@Component
@Order(40)
public class OrdenServicioDataInitializer implements CommandLineRunner {

    @Autowired private EventoRepository tipoEventoRepository;
    @Autowired private CategoriaEventoRepository categoriaTipoEventoRepository;

    @Override
    public void run(String... args) {
        if (tipoEventoRepository.existsById("SOA")) {
            return; // TipoEventoInitializer ya creó todos los eventos de orden
        }

        CategoriaEvento categoria = categoriaTipoEventoRepository
                .findByNombre("ORDEN")
                .orElseGet(() -> categoriaTipoEventoRepository.save(new CategoriaEvento("ORDEN")));

        tipoEventoRepository.save(new Evento("SOA", "ORDEN_SERVICIO_ASIGNADA", categoria));
    }
}
