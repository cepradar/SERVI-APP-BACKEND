package com.inventory.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "sedes")
public class Sede {

    @Id
    @Column(name = "codigo_sede", length = 10, nullable = false)
    private String codigoSede;

    @Column(nullable = false)
    private boolean activo = true;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(length = 200)
    private String direccion;

    @Column(length = 20)
    private String telefono;

    @Column(length = 100)
    private String email;

    /** FK hacia la tabla ciudades (código DANE). Nullable para compatibilidad. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ciudad_cod", referencedColumnName = "ciudad_cod", nullable = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Ciudad ciudadObj;

    /**
     * Consecutivo de ventas propio de esta sede.
     * Inicia en 1. Se incrementa atómicamente (con lock pesimista) al crear una venta.
     */
    @Column(name = "consecutivo_ventas", nullable = false)
    private Integer consecutivoVentas = 1;

    @Column(length = 2)
    private String prefijoCodigoVenta; // Ejemplo: "V-BQ-" para ventas, "O-BQ-" para órdenes
    /**
     * Consecutivo de órdenes de servicio propio de esta sede.
     * Inicia en 1. Se incrementa atómicamente (con lock pesimista) al crear una orden.
     */
    @Column(name = "consecutivo_ordenes", nullable = false)
    private Integer consecutivoOrdenes = 1;

    @Column(length = 2)
    private String prefijoCodigoOrden; // Ejemplo: "O-BQ-" para órdenes de servicio

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = LocalDateTime.now();
        this.fechaActualizacion = LocalDateTime.now();
        if (this.consecutivoVentas == null) this.consecutivoVentas = 1;
        if (this.consecutivoOrdenes == null) this.consecutivoOrdenes = 1;
    }

    @PreUpdate
    protected void onUpdate() {
        this.fechaActualizacion = LocalDateTime.now();
    }

    public Sede() {}

    public Sede(String codigoSede, String nombre) {
        this.codigoSede = codigoSede;
        this.nombre = nombre;
    }

    // ── Getters / Setters ───────────────────────────────────────────────────

    public String getCodigoSede() { return codigoSede; }
    public void setCodigoSede(String codigoSede) { this.codigoSede = codigoSede; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public Ciudad getCiudadObj() { return ciudadObj; }
    public void setCiudadObj(Ciudad ciudadObj) { this.ciudadObj = ciudadObj; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public Integer getConsecutivoVentas() { return consecutivoVentas; }
    public void setConsecutivoVentas(Integer consecutivoVentas) { this.consecutivoVentas = consecutivoVentas; }

    public String getPrefijoCodigoVenta() { return prefijoCodigoVenta; }
    public void setPrefijoCodigoVenta(String prefijoCodigoVenta) { this.prefijoCodigoVenta = prefijoCodigoVenta; }

    public String getPrefijoCodigoOrden() { return prefijoCodigoOrden; }
    public void setPrefijoCodigoOrden(String prefijoCodigoOrden) { this.prefijoCodigoOrden = prefijoCodigoOrden; }

    public Integer getConsecutivoOrdenes() { return consecutivoOrdenes; }
    public void setConsecutivoOrdenes(Integer consecutivoOrdenes) { this.consecutivoOrdenes = consecutivoOrdenes; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(LocalDateTime fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }

    /** Ciudad derivada de la relación ciudadObj (solo lectura). */
    public String getCiudad() { return ciudadObj != null ? ciudadObj.getNombre() : null; }

    /** Departamento derivado de la relación ciudadObj (solo lectura). */
    public String getDepartamento() { return ciudadObj != null ? ciudadObj.getDepartamento() : null; }

    @Override
    public String toString() {
        return "Sede{codigoSede='" + codigoSede + "', nombre='" + nombre + "', ciudad='" + getCiudad() + "'}";
    }
}
