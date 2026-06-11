package com.inventory.model;

import jakarta.persistence.*;

@Entity
@Table(name = "categoria_evento")
public class CategoriaEvento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean activo = true;

    public CategoriaEvento() {}

    public CategoriaEvento(String nombre) {
        this.nombre = nombre;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}
