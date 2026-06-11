package com.inventory.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entidad PROVEEDOR — Representa los proveedores o fabricantes de productos.
 * Esencial para el módulo de compras/ingresos de inventario.
 */
@Entity
@Table(name = "proveedores")
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Razón social o nombre comercial del proveedor. */
    @Column(nullable = false, length = 150)
    private String nombre;

    /** NIT o número de identificación tributaria. */
    @Column(length = 30, nullable = true, unique = true)
    private String nit;

    /** Persona de contacto principal. */
    @Column(name = "contacto_nombre", length = 100, nullable = true)
    private String contactoNombre;

    @Column(length = 20, nullable = true)
    private String telefono;

    @Column(length = 100, nullable = true)
    private String email;

    @Column(length = 200, nullable = true)
    private String direccion;

    @Column(length = 100, nullable = true)
    private String ciudad;

    @Column(length = 100, nullable = true)
    private String departamento;

    /** País del proveedor. Default: Colombia. */
    @Column(length = 80, nullable = true)
    private String pais = "Colombia";

    /** Sitio web del proveedor. */
    @Column(length = 200, nullable = true)
    private String sitioWeb;

    /** Condiciones de pago acordadas (p.ej. "30 días", "Contra entrega"). */
    @Column(name = "condiciones_pago", length = 100, nullable = true)
    private String condicionesPago;

    @Column(columnDefinition = "TEXT", nullable = true)
    private String observaciones;

    @Column(nullable = false)
    private boolean activo = true;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion", nullable = false)
    private LocalDateTime fechaActualizacion;

    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = LocalDateTime.now();
        this.fechaActualizacion = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.fechaActualizacion = LocalDateTime.now();
    }

    public Proveedor() {}

    public Proveedor(String nombre, String nit) {
        this.nombre = nombre;
        this.nit = nit;
    }

    // ── Getters / Setters ─────────────────────────────────────────────────────

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getNit() { return nit; }
    public void setNit(String nit) { this.nit = nit; }

    public String getContactoNombre() { return contactoNombre; }
    public void setContactoNombre(String contactoNombre) { this.contactoNombre = contactoNombre; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }

    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }

    public String getPais() { return pais; }
    public void setPais(String pais) { this.pais = pais; }

    public String getSitioWeb() { return sitioWeb; }
    public void setSitioWeb(String sitioWeb) { this.sitioWeb = sitioWeb; }

    public String getCondicionesPago() { return condicionesPago; }
    public void setCondicionesPago(String condicionesPago) { this.condicionesPago = condicionesPago; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
}
