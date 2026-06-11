package com.inventory.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inventory.model.CategoriaEvento;

@Repository
public interface CategoriaEventoRepository extends JpaRepository<CategoriaEvento, Long> {
    Optional<CategoriaEvento> findByNombre(String nombre);
}
