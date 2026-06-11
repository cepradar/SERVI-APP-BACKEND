package com.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para registro de clientes desde el landing page.
 * El email se usa como username del usuario (tabla usuarios).
 * El documento + tipoDocumento identifican al cliente (tabla clientes).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientRegisterRequest {

    private String email;         // Requerido — username en tabla usuarios
    private String password;      // Requerido — mínimo 6 caracteres
    private String firstName;     // Requerido — nombre
    private String lastName;      // Requerido — apellido
    private String telefono;      // Opcional
    private String nit;          // Requerido — número de cédula / NIT (PK en clientes)
    private String tipoDocumento; // Opcional — CC | NIT | CE | PASAPORTE | TI (default CC)
    private String direccion;     // Opcional
}
