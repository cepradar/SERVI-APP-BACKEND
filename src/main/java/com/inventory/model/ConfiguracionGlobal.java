package com.inventory.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "configuracion_global")
public class ConfiguracionGlobal {

    @Id
    @Column(length = 100, nullable = false)
    private String clave;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String valor;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "modificado_por", length = 120)
    private String modificadoPor;

    @Column(name = "fecha_modificacion")
    private LocalDateTime fechaModificacion;

    @PrePersist
    @PreUpdate
    protected void onSave() {
        this.fechaModificacion = LocalDateTime.now();
    }

    public ConfiguracionGlobal() {}

    public ConfiguracionGlobal(String clave, String valor, String descripcion) {
        this.clave = clave;
        this.valor = valor;
        this.descripcion = descripcion;
    }

    // ── Getters / Setters ─────────────────────────────────────────────────

    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }

    public String getValor() { return valor; }
    public void setValor(String valor) { this.valor = valor; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getModificadoPor() { return modificadoPor; }
    public void setModificadoPor(String modificadoPor) { this.modificadoPor = modificadoPor; }

    public LocalDateTime getFechaModificacion() { return fechaModificacion; }
    public void setFechaModificacion(LocalDateTime fechaModificacion) { this.fechaModificacion = fechaModificacion; }
}
