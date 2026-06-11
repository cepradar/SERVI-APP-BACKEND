package com.inventory.repository;

import com.inventory.model.Evento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventoRepository extends JpaRepository<Evento, String> {
    Evento findByNombre(String nombre);

    Optional<Evento> findByIdAndCategoriaId(String id, Long categoriaId);

    Optional<Evento> findByNombreAndCategoriaId(String nombre, Long categoriaId);
}