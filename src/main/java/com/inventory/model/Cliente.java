package com.inventory.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "clientes",
    uniqueConstraints = @UniqueConstraint(
        name = "uk_cliente_nit_tipo",
        columnNames = {"nit", "tipo_documento"}
    )
)
public class Cliente {

    /** PK única auto-generada — sustituye a la anterior PK compuesta. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tipo_documento", nullable = false)
    private DocumentoTipo tipoDocumento;

    /** Número de documento / NIT del cliente. */
    @Column(nullable = false, length = 30)
    private String nit;

    @Column(nullable = false, length = 30)
    private String nombre;
    
    @Column(nullable = false, length = 30)
    private String apellido;

    @Column(length = 20)
    private String telefono;

    @Column(length = 100)
    private String direccion;
    @Column(length = 100)
    private String email;


    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tipo_cliente", nullable = false)
    private CategoryClient category;

    /** FK hacia la tabla ciudades (código DANE). Nullable para compatibilidad. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ciudad_cod", referencedColumnName = "ciudad_cod", nullable = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Ciudad ciudadObj;

    @Column(name = "fecha_creacion", nullable = true, updatable = false)
    private LocalDateTime fechaCreacion;

    private Boolean activo;

    @PrePersist
    protected void onCreate() {
        if (this.fechaCreacion == null) this.fechaCreacion = LocalDateTime.now();
    }

    public Cliente() {}

    // ── Getters / Setters ─────────────────────────────────────────────────

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNit() { return nit; }
    public void setNit(String nit) { this.nit = nit; }

    public CategoryClient getCategory() {
        return category;
    }

    public void setCategory(CategoryClient category) {
        this.category = category;
    }

    public DocumentoTipo getTipoDocumento() {
        return tipoDocumento;
    }
    public void setTipoDocumento(DocumentoTipo tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getTipoDocumentoId() {
        return tipoDocumento != null ? tipoDocumento.getId() : null;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Ciudad getCiudadObj() { return ciudadObj; }
    public void setCiudadObj(Ciudad ciudadObj) { this.ciudadObj = ciudadObj; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }

}
