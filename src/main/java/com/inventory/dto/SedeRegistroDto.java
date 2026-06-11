package com.inventory.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SedeRegistroDto {

    @NotBlank(message = "El código de sede es obligatorio")
    @Size(min = 2, max = 10, message = "El código de sede debe tener entre 2 y 10 caracteres")
    private String codigoSede;

    @NotBlank(message = "El nombre de la sede es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String nombre;

    @Size(max = 200, message = "La dirección no puede exceder 200 caracteres")
    private String direccion;

    @Size(max = 100, message = "La ciudad no puede exceder 100 caracteres")
    private String ciudad;

    @Size(max = 100, message = "El departamento no puede exceder 100 caracteres")
    private String departamento;

    @Size(max = 20, message = "El teléfono no puede exceder 20 caracteres")
    private String telefono;

    @Email(message = "El email no tiene un formato válido")
    @Size(max = 100, message = "El email no puede exceder 100 caracteres")
    private String email;

    private Boolean activo;

    /** Prefijo configurable para IDs de ventas. Ej: "V" */
    private String prefijoVentas;
    /** Prefijo configurable para IDs de órdenes. Ej: "O" */
    private String prefijoOrdenes;

    // ── Getters / Setters ───────────────────────────────────────────────────

    public String getCodigoSede() { return codigoSede; }
    public void setCodigoSede(String codigoSede) { this.codigoSede = codigoSede; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }

    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }

    public String getPrefijoVentas() { return prefijoVentas; }
    public void setPrefijoVentas(String prefijoVentas) { this.prefijoVentas = prefijoVentas; }

    public String getPrefijoOrdenes() { return prefijoOrdenes; }
    public void setPrefijoOrdenes(String prefijoOrdenes) { this.prefijoOrdenes = prefijoOrdenes; }
}
