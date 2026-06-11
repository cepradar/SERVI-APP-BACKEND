package com.inventory.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Product {

    @Id
    @Column(unique = true)
    private String id;

    @Column(nullable = false)
    private boolean activo = true;

    private String name;
    private String description;

    /** Precio de venta al público. */
    private double price;

    /** Precio de compra / costo. Usado para calcular margen. */
    @Column(precision = 10, scale = 2, nullable = true)
    private BigDecimal costo;

    private int quantity;

    /** Stock mínimo para alertas de inventario bajo. */
    @Column(name = "stock_minimo", nullable = false)
    private int stockMinimo = 0;

    /** Unidad de medida: UNIDAD, KG, METRO, LITRO, etc. */
    @Column(name = "unidad_medida", length = 30, nullable = true)
    private String unidadMedida;

    /** Porcentaje de impuesto (IVA) aplicable a este producto. Ej: 19.00 = 19%. */
    @Column(name = "impuesto", precision = 5, scale = 2, nullable = false)
    private BigDecimal impuesto = BigDecimal.ZERO;

    @Column(name = "fecha_creacion", nullable = true, updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion", nullable = true)
    private LocalDateTime fechaActualizacion;

    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = LocalDateTime.now();
        this.fechaActualizacion = LocalDateTime.now();
        if (this.impuesto == null) this.impuesto = BigDecimal.ZERO;
    }

    @PreUpdate
    protected void onUpdate() {
        this.fechaActualizacion = LocalDateTime.now();
    }

    @JsonBackReference // Evita la serialización recursiva
    @ManyToOne(fetch = FetchType.EAGER)  // Puedes cambiarlo a LAZY si es necesario
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryProduct category;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoria_electrodomestico_id", nullable = true)
    private CategoriaElectrodomestico categoriaElectrodomestico;
    public Product() {}

    public Product(String name, double price, int quantity, CategoryProduct category) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public CategoryProduct getCategory() {
        return category;
    }

    public void setCategory(CategoryProduct category) {
        this.category = category;
    }

    public CategoriaElectrodomestico getCategoriaElectrodomestico() {
        return categoriaElectrodomestico;
    }

    public void setCategoriaElectrodomestico(CategoriaElectrodomestico categoriaElectrodomestico) {
        this.categoriaElectrodomestico = categoriaElectrodomestico;
    }

    public BigDecimal getCosto() { return costo; }
    public void setCosto(BigDecimal costo) { this.costo = costo; }

    public int getStockMinimo() { return stockMinimo; }
    public void setStockMinimo(int stockMinimo) { this.stockMinimo = stockMinimo; }

    public String getUnidadMedida() { return unidadMedida; }
    public void setUnidadMedida(String unidadMedida) { this.unidadMedida = unidadMedida; }

    public BigDecimal getImpuesto() { return impuesto; }
    public void setImpuesto(BigDecimal impuesto) { this.impuesto = impuesto; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }

    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

}
