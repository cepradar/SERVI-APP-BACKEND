package com.inventory.dto;

import com.inventory.model.Proveedor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ProveedorDto {

    private Long id;
    private String nombre;
    private String nit;
    private String contactoNombre;
    private String telefono;
    private String email;
    private String direccion;
    private String ciudad;
    private String departamento;
    private String pais;
    private String sitioWeb;
    private String condicionesPago;
    private String observaciones;
    private boolean activo;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    public ProveedorDto(Proveedor proveedor) {
        this.id = proveedor.getId();
        this.nombre = proveedor.getNombre();
        this.nit = proveedor.getNit();
        this.contactoNombre = proveedor.getContactoNombre();
        this.telefono = proveedor.getTelefono();
        this.email = proveedor.getEmail();
        this.direccion = proveedor.getDireccion();
        this.ciudad = proveedor.getCiudad();
        this.departamento = proveedor.getDepartamento();
        this.pais = proveedor.getPais();
        this.sitioWeb = proveedor.getSitioWeb();
        this.condicionesPago = proveedor.getCondicionesPago();
        this.observaciones = proveedor.getObservaciones();
        this.activo = proveedor.isActivo();
        this.fechaCreacion = proveedor.getFechaCreacion();
        this.fechaActualizacion = proveedor.getFechaActualizacion();
    }
}
