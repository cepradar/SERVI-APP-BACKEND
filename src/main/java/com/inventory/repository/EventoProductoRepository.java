package com.inventory.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.inventory.model.EventoProducto;

@Repository
public interface EventoProductoRepository extends JpaRepository<EventoProducto, String> {
    List<EventoProducto> findByProducto_Id(String productoId);
    List<EventoProducto> findByCliente_Nit(String nit);
    @Query("SELECT e FROM EventoProducto e WHERE e.cliente.nit = :nit AND e.cliente.tipoDocumento.id = :tipoDocumentoId")
    List<EventoProducto> findByCliente_NitAndCliente_TipoDocumentoId(@Param("nit") String nit, @Param("tipoDocumentoId") String tipoDocumentoId);
    List<EventoProducto> findByFechaEventoBetween(LocalDateTime desde, LocalDateTime hasta);
}
