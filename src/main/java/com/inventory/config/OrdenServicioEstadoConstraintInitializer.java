package com.inventory.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@Order(40)
public class OrdenServicioEstadoConstraintInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(OrdenServicioEstadoConstraintInitializer.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) {
        try {
                // Garantiza los codigos de tipo_evento para ORDEN (categoria_id = 2).
                jdbcTemplate.execute("""
                    INSERT INTO eventos (id, nombre, categoria_id) VALUES
                    ('SOC', 'ORDEN_SERVICIO_CREADA', 2),
                    ('SOA', 'ORDEN_SERVICIO_ASIGNADA', 2),
                    ('SOE', 'ORDEN_SERVICIO_EN_PROCESO', 2),
                    ('SOP', 'ORDEN_SERVICIO_PAUSADA', 2),
                    ('SOD', 'ORDEN_SERVICIO_DIAGNOSTICADA', 2),
                    ('SOR', 'ORDEN_SERVICIO_REPARADA', 2),
                    ('SOT', 'ORDEN_SERVICIO_PRUEBA', 2),
                    ('SOL', 'ORDEN_SERVICIO_LISTA', 2),
                    ('SOENT', 'ORDEN_SERVICIO_ENTREGADA', 2),
                    ('SOCAN', 'ORDEN_SERVICIO_CANCELADA', 2),
                    ('SOREC', 'ORDEN_SERVICIO_RECHAZADA', 2)
                    ON CONFLICT (id)
                    DO UPDATE SET
                        nombre = EXCLUDED.nombre,
                        categoria_id = EXCLUDED.categoria_id
                    """);
            logger.info("Eventos de estado de orden de servicio garantizados en la base de datos.");
        } catch (Exception e) {
            logger.error("Error al garantizar eventos de estado de orden de servicio: ", e);
        }
    }
}
