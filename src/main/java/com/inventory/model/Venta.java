package com.inventory.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "venta")
public class Venta {

    @Id
    @Column(name = "id", length = 30, nullable = false)
    private String id;

    /**
     * Sede donde se realizó la venta. FK a la tabla sedes.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codigo_sede", referencedColumnName = "codigo_sede",
                foreignKey = @ForeignKey(name = "fk_venta_sede"))
    private Sede sede;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<VentaDetalle> detalles;

    /**
     * FK compuesta hacia la tabla clientes (id + tipo_documento).
     * Reemplaza los campos legacy nombreComprador / telefonoComprador / emailComprador.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", referencedColumnName = "id",
                foreignKey = @ForeignKey(name = "fk_venta_cliente"))
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_username", nullable = false)
    private User usuario;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @Column
    private String observaciones;

    /**
     * Estado de la venta: PAGADA (default), PENDIENTE, ANULADA.
     * Se asigna PAGADA al crear para compatibilidad con registros existentes.
     */
    @Column(length = 20, nullable = false)
    private String estado = "PAGADA";

    /**
     * Forma de pago: EFECTIVO, TARJETA, TRANSFERENCIA, CREDITO, MIXTO, OTRO.
     */
    @Column(name = "forma_pago", length = 30, nullable = true)
    private String formaPago;

    /**
     * Descuento global aplicado a la venta (en moneda, no porcentaje).
     */
    @Column(name = "descuento", precision = 10, scale = 2, nullable = false)
    private BigDecimal descuento = BigDecimal.ZERO;

    @Column(name = "orden_de_servicio_id", nullable = true)
    private String ordenDeServicioId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "orden_de_servicio_id",
        referencedColumnName = "id",
        insertable = false,
        updatable = false,
        foreignKey = @ForeignKey(name = "fk_venta_orden_servicio")
    )
    private OrdenDeServicio ordenDeServicio;

    public Venta() {}

    private BigDecimal calcularTotalVenta() {
        if (detalles == null) return BigDecimal.ZERO;
        return detalles.stream()
                .map(VentaDetalle::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // ── Getters / Setters ───────────────────────────────────────────────────

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Sede getSede() { return sede; }
    public void setSede(Sede sede) { this.sede = sede; }

    public List<VentaDetalle> getDetalles() { return detalles; }
    public void setDetalles(List<VentaDetalle> detalles) {
        this.detalles = detalles;
    }

    public BigDecimal getTotalVenta() { return calcularTotalVenta(); }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public User getUsuario() { return usuario; }
    public void setUsuario(User usuario) { this.usuario = usuario; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getFormaPago() { return formaPago; }
    public void setFormaPago(String formaPago) { this.formaPago = formaPago; }

    public BigDecimal getDescuento() { return descuento != null ? descuento : BigDecimal.ZERO; }
    public void setDescuento(BigDecimal descuento) { this.descuento = descuento; }

    public String getOrdenDeServicioId() { return ordenDeServicioId; }
    public void setOrdenDeServicioId(String ordenDeServicioId) { this.ordenDeServicioId = ordenDeServicioId; }

    public OrdenDeServicio getOrdenDeServicio() { return ordenDeServicio; }
    public void setOrdenDeServicio(OrdenDeServicio ordenDeServicio) { this.ordenDeServicio = ordenDeServicio; }

    @Override
    public String toString() {
        return "Venta{id='" + id + "'"
                + ", sede=" + (sede != null ? sede.getCodigoSede() : "null")
                + ", totalVenta=" + getTotalVenta()
                + ", cliente=" + (cliente != null ? cliente.getId() : "null")
                + ", usuario=" + (usuario != null ? usuario.getUsername() : "null")
                + ", fecha=" + fecha + "}";
    }
}