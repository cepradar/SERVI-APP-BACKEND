package com.inventory.dto;

import com.inventory.model.Company;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDto {
    private Long id;
    private String razonSocial;
    private String nit;
    private String direccion;
    private String ciudad;
    private String codigoPostal;
    private String telefono;
    private String correo;
    private String sitioWeb;
    private String representanteLegal;
    private String numeroRegimen;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    // Constructor desde Company
    public CompanyDto(Company company) {
        this.id = company.getId();
        this.razonSocial = company.getRazonSocial();
        this.nit = company.getNit();
        this.direccion = company.getDireccion();
        this.ciudad = company.getCiudadObj() != null ? company.getCiudadObj().getNombre() : null;
        this.codigoPostal = company.getCodigoPostal();
        this.telefono = company.getTelefono();
        this.correo = company.getCorreo();
        this.sitioWeb = company.getSitioWeb();
        this.representanteLegal = company.getRepresentanteLegal();
        this.numeroRegimen = company.getNumeroRegimen();
        this.fechaCreacion = company.getFechaCreacion();
        this.fechaActualizacion = company.getFechaActualizacion();
    }

    // Convertir DTO a Entity
    public static Company toCompany(CompanyDto dto) {
        Company company = new Company();
        company.setId(dto.getId());
        company.setRazonSocial(dto.getRazonSocial());
        company.setNit(dto.getNit());
        company.setDireccion(dto.getDireccion());
        // Aquí se debe manejar la conversión de ciudad desde DTO a entidad
        // Esto puede requerir buscar la entidad Ciudad correspondiente en la base de datos
        company.setCodigoPostal(dto.getCodigoPostal());
        company.setTelefono(dto.getTelefono());
        company.setCorreo(dto.getCorreo());
        company.setSitioWeb(dto.getSitioWeb());
        company.setRepresentanteLegal(dto.getRepresentanteLegal());
        company.setNumeroRegimen(dto.getNumeroRegimen());
        return company;
    }
}
