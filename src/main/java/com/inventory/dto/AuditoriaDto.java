package com.inventory.dto;

import com.inventory.model.Auditoria;
import java.time.LocalDateTime;

public class AuditoriaDto {

    private Long id;
    private String eventoId;
    private String eventoNombre;
    private String eventoCategoria;
    private String descripcion;
    private String usuarioUsername;
    private String usuarioNombreCompleto;
    private LocalDateTime fecha;
    private String estadoInicial;
    private String estadoFinal;
    private String productoId;
    private String productoNombre;
    private String ventaId;
    private String ordenServicioId;

    public AuditoriaDto() {}

    public AuditoriaDto(Auditoria a) {
        this.id            = a.getId();
        this.descripcion   = a.getDescripcion();
        this.fecha         = a.getFecha();
        this.estadoInicial = a.getEstadoInicial();
        this.estadoFinal   = a.getEstadoFinal();
        if (a.getEvento() != null) {
            this.eventoId       = a.getEvento().getId();
            this.eventoNombre   = a.getEvento().getNombre();
            this.eventoCategoria = a.getEvento().getCategoria() != null
                    ? a.getEvento().getCategoria().getNombre() : "SIN_CATEGORIA";
        } else {
            this.eventoId = "DESCONOCIDO"; this.eventoNombre = "Desconocido"; this.eventoCategoria = "SIN_CATEGORIA";
        }
        if (a.getUsuario() != null) {
            this.usuarioUsername = a.getUsuario().getUsername();
            this.usuarioNombreCompleto =
                    (a.getUsuario().getFirstName() != null ? a.getUsuario().getFirstName() : "")
                    + " " + (a.getUsuario().getLastName() != null ? a.getUsuario().getLastName() : "");
        }
        if (a.getProducto() != null) { this.productoId = a.getProducto().getId(); this.productoNombre = a.getProducto().getName(); }
        if (a.getVenta() != null) this.ventaId = a.getVenta().getId();
        if (a.getOrdenDeServicio() != null) this.ordenServicioId = a.getOrdenDeServicio().getId();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEventoId() { return eventoId; }
    public void setEventoId(String eventoId) { this.eventoId = eventoId; }
    public String getEventoNombre() { return eventoNombre; }
    public void setEventoNombre(String eventoNombre) { this.eventoNombre = eventoNombre; }
    public String getEventoCategoria() { return eventoCategoria; }
    public void setEventoCategoria(String eventoCategoria) { this.eventoCategoria = eventoCategoria; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getUsuarioUsername() { return usuarioUsername; }
    public void setUsuarioUsername(String u) { this.usuarioUsername = u; }
    public String getUsuarioNombreCompleto() { return usuarioNombreCompleto; }
    public void setUsuarioNombreCompleto(String v) { this.usuarioNombreCompleto = v; }
    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
    public String getEstadoInicial() { return estadoInicial; }
    public void setEstadoInicial(String estadoInicial) { this.estadoInicial = estadoInicial; }
    public String getEstadoFinal() { return estadoFinal; }
    public void setEstadoFinal(String estadoFinal) { this.estadoFinal = estadoFinal; }
    public String getProductoId() { return productoId; }
    public void setProductoId(String productoId) { this.productoId = productoId; }
    public String getProductoNombre() { return productoNombre; }
    public void setProductoNombre(String productoNombre) { this.productoNombre = productoNombre; }
    public String getVentaId() { return ventaId; }
    public void setVentaId(String ventaId) { this.ventaId = ventaId; }
    public String getOrdenServicioId() { return ordenServicioId; }
    public void setOrdenServicioId(String ordenServicioId) { this.ordenServicioId = ordenServicioId; }
}