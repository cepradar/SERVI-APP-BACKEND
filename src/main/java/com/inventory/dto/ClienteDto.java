package com.inventory.dto;

import com.inventory.model.Cliente;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClienteDto {

    private Long id;           // PK auto-generado
    private String nit;        // Número de documento del cliente
    private String categoryId;
    private String categoryName;
    private String tipoDocumentoId;
    private String tipoDocumentoName;
    private String nombre;
    private String apellido;
    private String telefono;
    private String direccion;
    private Boolean activo;
    private String email;
    private String ciudad;
    private String ciudadCod;
    private String departamento;
    private String observaciones;

    public ClienteDto(Cliente cliente) {
        this.id  = cliente.getId();
        this.nit = cliente.getNit();

        if (cliente.getCategory() != null) {
            this.categoryId   = cliente.getCategory().getId();
            this.categoryName = cliente.getCategory().getName();
        }

        if (cliente.getTipoDocumento() != null) {
            this.tipoDocumentoId   = cliente.getTipoDocumento().getId();
            this.tipoDocumentoName = cliente.getTipoDocumento().getName();
        } else {
            this.tipoDocumentoId = cliente.getTipoDocumentoId();
        }

        this.nombre      = cliente.getNombre();
        this.apellido    = cliente.getApellido();
        this.telefono    = cliente.getTelefono();
        this.direccion   = cliente.getDireccion();
        this.activo      = cliente.getActivo();
        this.email       = cliente.getEmail();

        if (cliente.getCiudadObj() != null) {
            this.ciudadCod = cliente.getCiudadObj().getCiudadCod();
            this.ciudad    = cliente.getCiudadObj().getNombre();
        }
    }
}
