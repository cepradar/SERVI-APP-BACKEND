package com.inventory.dto;

import java.time.LocalDateTime;

public class SedeDto {

    private String codigoSede;
    private String nombre;
    private String direccion;
    private String ciudad;
    private String departamento;
    private String telefono;
    private String email;
    private boolean activo;
    /** Prefijo configurable para IDs de ventas. Ej: "V" → genera V-BQ001-000001 */
    private String prefijoVentas;
    /** Prefijo configurable para IDs de órdenes. Ej: "O" → genera O-BQ001-000001 */
    private String prefijoOrdenes;
    private Integer consecutivoVentas;
    private Integer consecutivoOrdenes;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    public SedeDto() {}

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

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public String getPrefijoVentas() { return prefijoVentas; }
    public void setPrefijoVentas(String prefijoVentas) { this.prefijoVentas = prefijoVentas; }

    public String getPrefijoOrdenes() { return prefijoOrdenes; }
    public void setPrefijoOrdenes(String prefijoOrdenes) { this.prefijoOrdenes = prefijoOrdenes; }

    public Integer getConsecutivoVentas() { return consecutivoVentas; }
    public void setConsecutivoVentas(Integer consecutivoVentas) { this.consecutivoVentas = consecutivoVentas; }

    public Integer getConsecutivoOrdenes() { return consecutivoOrdenes; }
    public void setConsecutivoOrdenes(Integer consecutivoOrdenes) { this.consecutivoOrdenes = consecutivoOrdenes; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(LocalDateTime fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }
}
