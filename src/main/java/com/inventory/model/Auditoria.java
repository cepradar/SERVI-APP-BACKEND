package com.inventory.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "auditoria")
public class Auditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @ManyToOne
    @JoinColumn(name = "evento_id", nullable = false)
    private Evento evento;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "usuario_username", nullable = false)
    private User usuario;

    @Column(name = "estado_inicial", columnDefinition = "TEXT")
    private String estadoInicial;

    @Column(name = "estado_final", columnDefinition = "TEXT")
    private String estadoFinal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", nullable = true, foreignKey = @ForeignKey(name = "fk_auditoria_producto"))
    private Product producto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venta_id", nullable = true, foreignKey = @ForeignKey(name = "fk_auditoria_venta"))
    private Venta venta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orden_servicio_id", nullable = true, foreignKey = @ForeignKey(name = "fk_auditoria_orden"))
    private OrdenDeServicio ordenDeServicio;

    public Auditoria() {}

    public Auditoria(Evento evento, String descripcion, User usuario, String estadoInicial, String estadoFinal) {
        this.evento = evento;
        this.descripcion = descripcion;
        this.usuario = usuario;
        this.fecha = LocalDateTime.now();
        this.estadoInicial = estadoInicial;
        this.estadoFinal = estadoFinal;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Evento getEvento() { return evento; }
    public void setEvento(Evento evento) { this.evento = evento; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public User getUsuario() { return usuario; }
    public void setUsuario(User usuario) { this.usuario = usuario; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public String getEstadoInicial() { return estadoInicial; }
    public void setEstadoInicial(String estadoInicial) { this.estadoInicial = estadoInicial; }

    public String getEstadoFinal() { return estadoFinal; }
    public void setEstadoFinal(String estadoFinal) { this.estadoFinal = estadoFinal; }

    public Product getProducto() { return producto; }
    public void setProducto(Product producto) { this.producto = producto; }

    public Venta getVenta() { return venta; }
    public void setVenta(Venta venta) { this.venta = venta; }

    public OrdenDeServicio getOrdenDeServicio() { return ordenDeServicio; }
    public void setOrdenDeServicio(OrdenDeServicio ordenDeServicio) { this.ordenDeServicio = ordenDeServicio; }

    @Override
    public String toString() {
        return "Auditoria{id=" + id + ", evento=" + (evento != null ? evento.getId() : "null") + ", fecha=" + fecha + "}";
    }
}