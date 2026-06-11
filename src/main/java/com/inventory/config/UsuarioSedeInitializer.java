package com.inventory.config;

import org.springframework.context.annotation.Configuration;

/**
 * Inicializador de datos de usuario-sede.
 *
 * Las asociaciones usuario ↔ sede se gestionan desde el panel de administración
 * en Configuración → Usuarios. No se crean asociaciones automáticas al arrancar
 * para evitar dependencias en el orden de creación de entidades.
 */
@Configuration
public class UsuarioSedeInitializer {
    // Sin beans de inicialización: las sedes y sus asignaciones se crean
    // manualmente desde la interfaz de administración.
}
