package com.inventory.repository;

import com.inventory.model.Auditoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuditoriaRepository extends JpaRepository<Auditoria, Long> {

    // Buscar por tipo de evento
    List<Auditoria> findByEventoId(String eventoId);

    // Buscar por varios tipos de evento
    List<Auditoria> findByEventoIdIn(List<String> eventoIds);

    // Buscar por producto
    List<Auditoria> findByProductoId(String productoId);

    // Buscar por usuario
    @Query("SELECT a FROM Auditoria a WHERE UPPER(a.usuario.username) = UPPER(?1)")
    List<Auditoria> findByUsuarioUsernameIgnoreCase(String username);

    // Buscar en rango de fechas
    List<Auditoria> findByFechaBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);

    // Ordenar por fecha descendente
    List<Auditoria> findAllByOrderByFechaDesc();

    // Buscar por categoría de evento
    @Query("SELECT a FROM Auditoria a WHERE a.evento.categoria.nombre = ?1 ORDER BY a.fecha DESC")
    List<Auditoria> findByEventoCategoria(String categoria);

    // Buscar por categoria de evento usando id
    @Query("SELECT a FROM Auditoria a WHERE a.evento.categoria.id = ?1 ORDER BY a.fecha DESC")
    List<Auditoria> findByEventoCategoriaId(Long categoriaId);

    // Todo lo relacionado con ordenes: categoria ORDEN o codigos SO*
    @Query("""
        SELECT a FROM Auditoria a
        WHERE a.evento.categoria.id = 2
           OR a.evento.id LIKE 'SO%'
        ORDER BY a.fecha DESC
        """)
    List<Auditoria> findMovimientosOrdenes();
}
