package com.inventory.model;

import jakarta.persistence.*;

/**
 * Ciudad de Colombia con código DANE oficial.
 * Solo las ciudades de la Costa Caribe y San Andrés inician con activo=true.
 */
@Entity
@Table(name = "ciudades")
public class Ciudad {

    /** Código DANE de 5 dígitos (municipio): departamento(2) + municipio(3). */
    @Id
    @Column(name = "ciudad_cod", length = 8, nullable = false)
    private String ciudadCod;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String departamento;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean activo = false;

    public Ciudad() {}

    public Ciudad(String ciudadCod, String nombre, String departamento, boolean activo) {
        this.ciudadCod = ciudadCod;
        this.nombre = nombre;
        this.departamento = departamento;
        this.activo = activo;
    }

    public String getCiudadCod() { return ciudadCod; }
    public void setCiudadCod(String ciudadCod) { this.ciudadCod = ciudadCod; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}
