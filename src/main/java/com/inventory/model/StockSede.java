package com.inventory.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entidad STOCK_SEDE — Registra el stock disponible de un producto en una sede específica.
 * Permite gestionar inventario distribuido entre múltiples sedes.
 */
@Entity
@Table(
    name = "stock_sede",
    uniqueConstraints = @UniqueConstraint(
        name = "uk_stock_sede_producto_sede",
        columnNames = {"producto_id", "codigo_sede"}
    )
)
public class StockSede {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", nullable = false)
    private Product producto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codigo_sede", nullable = false)
    private Sede sede;

    /** Cantidad disponible de este producto en esta sede. */
    @Column(nullable = false)
    private int cantidad = 0;

    /** Stock mínimo antes de generar alerta de reposición. */
    @Column(name = "stock_minimo", nullable = false)
    private int stockMinimo = 0;

    @Column(name = "fecha_actualizacion", nullable = true)
    private LocalDateTime fechaActualizacion;

    @PrePersist
    @PreUpdate
    protected void onSave() {
        this.fechaActualizacion = LocalDateTime.now();
    }

    public StockSede() {}

    public StockSede(Product producto, Sede sede, int cantidad) {
        this.producto = producto;
        this.sede = sede;
        this.cantidad = cantidad;
    }

    // ── Getters / Setters ─────────────────────────────────────────────────────

    public Long getId() { return id; }

    public Product getProducto() { return producto; }
    public void setProducto(Product producto) { this.producto = producto; }

    public Sede getSede() { return sede; }
    public void setSede(Sede sede) { this.sede = sede; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public int getStockMinimo() { return stockMinimo; }
    public void setStockMinimo(int stockMinimo) { this.stockMinimo = stockMinimo; }

    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
}
