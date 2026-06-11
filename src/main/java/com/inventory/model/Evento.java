package com.inventory.model;

import jakarta.persistence.*;

@Entity
@Table(name = "eventos")
public class Evento {

    @Id
    @Column(nullable = false, unique = true)
    private String id;

    @Column(nullable = false, unique = true)
    private String nombre;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "categoria_id", nullable = false)
    private CategoriaEvento categoria;

    // Constructor vacío requerido por JPA
    public Evento() {}

    public Evento(String id, String nombre, CategoriaEvento categoria) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public CategoriaEvento getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaEvento categoria) {
        this.categoria = categoria;
    }
}
