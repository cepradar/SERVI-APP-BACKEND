package com.inventory.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.lang.NonNull;

import com.inventory.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query("SELECT c FROM Cliente c WHERE c.nit = ?1 AND c.tipoDocumento.id = ?2")
    Optional<Cliente> findByNitAndTipoDocumentoId(@NonNull String nit, @NonNull String tipoDocumentoId);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Cliente c WHERE c.nit = ?1 AND c.tipoDocumento.id = ?2")
    boolean existsByNitAndTipoDocumentoId(@NonNull String nit, @NonNull String tipoDocumentoId);

    @Query("SELECT c FROM Cliente c WHERE c.nit = ?1")
    List<Cliente> findByNit(@NonNull String nit);

    List<Cliente> findByActivoTrue();
}
