package com.inventory.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.math.BigDecimal;

public class VentaDto {
    /** ID de la venta = código legible. Formato: V-{CODIGO_SEDE}-{CONSECUTIVO}. Ej: V-BQ-000115 */
    private String id;
    /** Código de la sede donde se realizó la venta. */
    private String codigoSede;
    /** Nombre de la sede para mostrar en UI. */
    private String nombreSede;
    private BigDecimal totalVenta;
    /** Nombre + apellido derivado de la FK cliente. Solo lectura. */
    private String nombreComprador;
    /** Teléfono derivado de la FK cliente. Solo lectura. */
    private String telefonoComprador;
    /** Email derivado de la FK cliente. Solo lectura. */
    private String emailComprador;
    /** Documento del cliente (parte 1 de la llave compuesta). */
    private String clienteId;
    /** Tipo de documento del cliente (parte 2 de la llave compuesta). */
    private String clienteTipoDocumento;
    private String usuarioUsername;
    private String usuarioNombre;
    private LocalDateTime fecha;
    private String observaciones;
    private String ordenDeServicioId;
    /** Estado de la venta: PAGADA, PENDIENTE, ANULADA */
    private String estado;
    /** Forma de pago: EFECTIVO, TARJETA, TRANSFERENCIA, CREDITO, MIXTO, OTRO */
    private String formaPago;
    /** Descuento global aplicado (en moneda) */
    private java.math.BigDecimal descuento;
    private List<VentaDetalleDto> detalles;

    public VentaDto() {}

    public VentaDto(String id, BigDecimal totalVenta, String nombreComprador, String telefonoComprador,
                    String emailComprador, String usuarioUsername, String usuarioNombre,
                    LocalDateTime fecha, String observaciones, List<VentaDetalleDto> detalles) {
        this.id = id;
        this.totalVenta = totalVenta;
        this.nombreComprador = nombreComprador;
        this.telefonoComprador = telefonoComprador;
        this.emailComprador = emailComprador;
        this.usuarioUsername = usuarioUsername;
        this.usuarioNombre = usuarioNombre;
        this.fecha = fecha;
        this.observaciones = observaciones;
        this.detalles = detalles;
    }

    // Getters y Setters

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getCodigoSede() { return codigoSede; }
    public void setCodigoSede(String codigoSede) { this.codigoSede = codigoSede; }

    public String getNombreSede() { return nombreSede; }
    public void setNombreSede(String nombreSede) { this.nombreSede = nombreSede; }

    public BigDecimal getTotalVenta() { return totalVenta; }
    public void setTotalVenta(BigDecimal totalVenta) { this.totalVenta = totalVenta; }

    public String getNombreComprador() { return nombreComprador; }
    public void setNombreComprador(String nombreComprador) { this.nombreComprador = nombreComprador; }

    public String getTelefonoComprador() { return telefonoComprador; }
    public void setTelefonoComprador(String telefonoComprador) { this.telefonoComprador = telefonoComprador; }

    public String getEmailComprador() { return emailComprador; }
    public void setEmailComprador(String emailComprador) { this.emailComprador = emailComprador; }

    public String getUsuarioUsername() { return usuarioUsername; }
    public void setUsuarioUsername(String usuarioUsername) { this.usuarioUsername = usuarioUsername; }

    public String getUsuarioNombre() { return usuarioNombre; }
    public void setUsuarioNombre(String usuarioNombre) { this.usuarioNombre = usuarioNombre; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public String getOrdenDeServicioId() { return ordenDeServicioId; }
    public void setOrdenDeServicioId(String ordenDeServicioId) { this.ordenDeServicioId = ordenDeServicioId; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getFormaPago() { return formaPago; }
    public void setFormaPago(String formaPago) { this.formaPago = formaPago; }

    public java.math.BigDecimal getDescuento() { return descuento; }
    public void setDescuento(java.math.BigDecimal descuento) { this.descuento = descuento; }

    public String getClienteId() { return clienteId; }
    public void setClienteId(String clienteId) { this.clienteId = clienteId; }

    public String getClienteTipoDocumento() { return clienteTipoDocumento; }
    public void setClienteTipoDocumento(String clienteTipoDocumento) { this.clienteTipoDocumento = clienteTipoDocumento; }

    public List<VentaDetalleDto> getDetalles() { return detalles; }
    public void setDetalles(List<VentaDetalleDto> detalles) { this.detalles = detalles; }
}