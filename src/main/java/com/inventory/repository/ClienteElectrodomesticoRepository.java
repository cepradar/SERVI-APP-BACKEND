package com.inventory.repository;

import com.inventory.model.ClienteElectrodomestico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteElectrodomesticoRepository extends JpaRepository<ClienteElectrodomestico, Long> {
    
    Optional<ClienteElectrodomestico> findByNumeroSerie(String numeroSerie);

    // Búsqueda por nit del cliente (campo String)
    List<ClienteElectrodomestico> findByClienteNit(String nit);
    @Query("SELECT ce FROM ClienteElectrodomestico ce WHERE ce.cliente.nit = :nit AND ce.cliente.tipoDocumento.id = :tipoDocumentoId")
    List<ClienteElectrodomestico> findByClienteNitAndClienteTipoDocumentoId(@Param("nit") String nit, @Param("tipoDocumentoId") String tipoDocumentoId);

    List<ClienteElectrodomestico> findByEstado(String estado);

    List<ClienteElectrodomestico> findByClienteNitAndEstado(String nit, String estado);
    @Query("SELECT ce FROM ClienteElectrodomestico ce WHERE ce.cliente.nit = :nit AND ce.cliente.tipoDocumento.id = :tipoDocumentoId AND ce.estado = :estado")
    List<ClienteElectrodomestico> findByClienteNitAndClienteTipoDocumentoIdAndEstado(@Param("nit") String nit, @Param("tipoDocumentoId") String tipoDocumentoId, @Param("estado") String estado);

    @Query("SELECT ce FROM ClienteElectrodomestico ce WHERE ce.cliente.nit = :nit AND ce.garantiaVigente = true")
    List<ClienteElectrodomestico> findConGarantiaVigenteByCliente(@Param("nit") String nit);

    @Query("SELECT ce FROM ClienteElectrodomestico ce WHERE ce.cliente.nit = :nit AND ce.cliente.tipoDocumento.id = :tipoDocumentoId AND ce.garantiaVigente = true")
    List<ClienteElectrodomestico> findConGarantiaVigenteByCliente(@Param("nit") String nit, @Param("tipoDocumentoId") String tipoDocumentoId);

    @Query("SELECT ce FROM ClienteElectrodomestico ce WHERE ce.fechaVencimientoGarantia BETWEEN :desde AND :hasta AND ce.garantiaVigente = true")
    List<ClienteElectrodomestico> findGarantiasPorVencer(@Param("desde") LocalDate desde, @Param("hasta") LocalDate hasta);

    // Validar unicidad de serial + marca + cliente
    boolean existsByNumeroSerieAndMarcaElectrodomesticoIdAndClienteNit(String numeroSerie, Long marcaElectrodomesticoId, String nit);
    @Query("SELECT CASE WHEN COUNT(ce) > 0 THEN true ELSE false END FROM ClienteElectrodomestico ce WHERE ce.numeroSerie = :numeroSerie AND ce.marcaElectrodomestico.id = :marcaId AND ce.cliente.nit = :nit AND ce.cliente.tipoDocumento.id = :tipoDocumentoId")
    boolean existsByNumeroSerieAndMarcaElectrodomesticoIdAndClienteNitAndClienteTipoDocumentoId(@Param("numeroSerie") String numeroSerie, @Param("marcaId") Long marcaElectrodomesticoId, @Param("nit") String nit, @Param("tipoDocumentoId") String tipoDocumentoId);
}
