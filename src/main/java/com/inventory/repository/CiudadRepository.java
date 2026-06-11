package com.inventory.repository;

import com.inventory.model.Ciudad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CiudadRepository extends JpaRepository<Ciudad, String> {
    List<Ciudad> findByActivoTrue();
    List<Ciudad> findByDepartamentoIgnoreCase(String departamento);
}
