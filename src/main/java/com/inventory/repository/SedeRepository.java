package com.inventory.repository;

import com.inventory.model.Sede;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SedeRepository extends JpaRepository<Sede, String> {

    List<Sede> findByActivoTrue();

    List<Sede> findByActivoFalse();

    Optional<Sede> findByNombreIgnoreCase(String nombre);

    List<Sede> findByNombreContainingIgnoreCase(String nombre);

    /**
     * Obtiene la sede con LOCK PESSIMISTIC_WRITE para garantizar que el incremento
     * de consecutivos sea thread-safe bajo concurrencia alta.
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM Sede s WHERE s.codigoSede = :codigoSede")
    Optional<Sede> findByCodigoSedeParaActualizacion(@Param("codigoSede") String codigoSede);
}
