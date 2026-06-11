package com.inventory.repository;

import com.inventory.model.OrdenDeServicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrdenDeServicioRepository extends JpaRepository<OrdenDeServicio, String> {

    @Query(value = "SELECT id FROM orden_de_servicio ORDER BY id DESC LIMIT 1", nativeQuery = true)
    String findUltimoId();
    
    @Query("SELECT s FROM OrdenDeServicio s WHERE s.cliente.nit = :nit ORDER BY s.fechaIngreso DESC")
    List<OrdenDeServicio> findByClienteId(@Param("nit") String nit);

    @Query("SELECT s FROM OrdenDeServicio s WHERE s.cliente.nit = :nit AND s.cliente.tipoDocumento.id = :tipoDocumentoId ORDER BY s.fechaIngreso DESC")
    List<OrdenDeServicio> findByClienteIdAndTipoDocumentoId(@Param("nit") String nit, @Param("tipoDocumentoId") String tipoDocumentoId);
    
    @Query("SELECT s FROM OrdenDeServicio s WHERE s.clienteElectrodomestico.id = :clienteElectrodomesticoId ORDER BY s.fechaIngreso DESC")
    List<OrdenDeServicio> findByClienteElectrodomesticoId(Long clienteElectrodomesticoId);
    
    @Query("SELECT s FROM OrdenDeServicio s WHERE s.estado.nombre = :estado ORDER BY s.fechaIngreso DESC")
    List<OrdenDeServicio> findByEstado(String estado);
    
    @Query("SELECT s FROM OrdenDeServicio s WHERE s.tipoServicio = :tipoServicio ORDER BY s.fechaIngreso DESC")
    List<OrdenDeServicio> findByTipoServicio(String tipoServicio);
    
    @Query("SELECT s FROM OrdenDeServicio s WHERE s.usuario.username = :username ORDER BY s.fechaIngreso DESC")
    List<OrdenDeServicio> findByUsuarioUsername(String username);
    
    @Query("SELECT s FROM OrdenDeServicio s WHERE s.fechaIngreso >= :fechaInicio AND s.fechaIngreso <= :fechaFin ORDER BY s.fechaIngreso DESC")
    List<OrdenDeServicio> findByFechaIngresoRango(java.time.LocalDateTime fechaInicio, java.time.LocalDateTime fechaFin);
    
    @Query("SELECT s FROM OrdenDeServicio s WHERE s.estado.nombre != 'SOENT' AND s.estado.nombre != 'SOCAN' ORDER BY s.fechaIngreso ASC")
    List<OrdenDeServicio> findServiciosPendientes();
    
    @Query("SELECT s FROM OrdenDeServicio s WHERE s.vencimientoGarantia IS NOT NULL AND s.vencimientoGarantia >= :hoy AND s.vencimientoGarantia <= :proximosDias ORDER BY s.vencimientoGarantia ASC")
    List<OrdenDeServicio> findGarantiasPorVencer(LocalDate hoy, LocalDate proximosDias);
    
    @Query("SELECT s FROM OrdenDeServicio s WHERE s.cliente.nit = :nit AND s.estado.nombre = :estado ORDER BY s.fechaIngreso DESC")
    List<OrdenDeServicio> findByClienteIdAndEstado(@Param("nit") String nit, @Param("estado") String estado);

    @Query("SELECT s FROM OrdenDeServicio s WHERE s.cliente.nit = :nit AND s.cliente.tipoDocumento.id = :tipoDocumentoId AND s.estado.nombre = :estado ORDER BY s.fechaIngreso DESC")
    List<OrdenDeServicio> findByClienteIdAndTipoDocumentoIdAndEstado(@Param("nit") String nit, @Param("tipoDocumentoId") String tipoDocumentoId, @Param("estado") String estado);

    /**
     * Regla 2: devuelve las órdenes cuyo estado corresponde al TipoEvento con
     * nombre = 'ORDEN_SERVICIO_CREADA', independientemente del código/id que tenga
     * ese tipo_evento en la base de datos.
     */
    @Query("SELECT s FROM OrdenDeServicio s " +
           "WHERE s.estado IN (SELECT te.categoria FROM Evento te WHERE te.nombre = 'ORDEN_SERVICIO_CREADA') " +
           "ORDER BY s.fechaIngreso DESC")
    List<OrdenDeServicio> findOrdenesParaAsignar();

    /**
     * Regla 4: devuelve las órdenes asignadas al técnico cuyo username coincide
     * con la columna tecnico_asignado_username de la entidad.
     */
    @Query("SELECT s FROM OrdenDeServicio s " +
           "WHERE s.tecnicoAsignado.username = :username " +
           "ORDER BY s.fechaIngreso DESC")
    List<OrdenDeServicio> findByTecnicoAsignadoUsername(@Param("username") String username);

    /**
     * Devuelve órdenes listas para entregar: estado = LISTA o REPARADA.
     * Usa subquery sobre tipo_evento.nombre para ser robusto ante distintos códigos en BD.
     */
    @Query("SELECT s FROM OrdenDeServicio s " +
           "WHERE s.estado IN (SELECT te.categoria FROM Evento te " +
           "  WHERE te.nombre IN ('ORDEN_SERVICIO_LISTA', 'ORDEN_SERVICIO_REPARADA')) " +
           "ORDER BY s.fechaIngreso DESC")
    List<OrdenDeServicio> findOrdenesParaEntregar();
}
