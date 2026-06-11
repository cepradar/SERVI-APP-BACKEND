package com.inventory.repository;

import com.inventory.model.ConfiguracionGlobal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfiguracionGlobalRepository extends JpaRepository<ConfiguracionGlobal, String> {

    /** Busca todos los parámetros cuya clave empieza con un prefijo dado. */
    List<ConfiguracionGlobal> findByClaveStartingWith(String prefijo);
}
