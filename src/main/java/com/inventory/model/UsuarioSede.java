package com.inventory.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Relación muchos-a-muchos entre Usuario y Sede.
 * Define a qué sedes tiene acceso operativo un usuario.
 */
@Entity
@Table(
    name = "usuario_sede",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_usuario_sede", columnNames = {"usuario_username", "codigo_sede"})
    }
)
public class UsuarioSede {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_username", nullable = false,
                foreignKey = @ForeignKey(name = "fk_usuario_sede_usuario"))
    private User usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codigo_sede", nullable = false,
                foreignKey = @ForeignKey(name = "fk_usuario_sede_sede"))
    private Sede sede;

    /** Permite suspender el acceso sin eliminar el registro. */
    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean activo = true;

    @Column(name = "fecha_asignacion", nullable = true, updatable = false)
    private LocalDateTime fechaAsignacion;

    @PrePersist
    protected void onCreate() {
        if (this.fechaAsignacion == null) this.fechaAsignacion = LocalDateTime.now();
    }

    public UsuarioSede() {}

    public UsuarioSede(User usuario, Sede sede) {
        this.usuario = usuario;
        this.sede = sede;
    }

    // ── Getters / Setters ───────────────────────────────────────────────────

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUsuario() { return usuario; }
    public void setUsuario(User usuario) { this.usuario = usuario; }

    public Sede getSede() { return sede; }
    public void setSede(Sede sede) { this.sede = sede; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public LocalDateTime getFechaAsignacion() { return fechaAsignacion; }

    @Override
    public String toString() {
        return "UsuarioSede{id=" + id
            + ", usuario=" + (usuario != null ? usuario.getUsername() : "null")
            + ", sede=" + (sede != null ? sede.getCodigoSede() : "null") + "}";
    }
}
