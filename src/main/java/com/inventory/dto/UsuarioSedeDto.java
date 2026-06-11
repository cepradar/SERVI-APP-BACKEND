package com.inventory.dto;

public class UsuarioSedeDto {

    private Long id;
    private String usuarioUsername;
    private String usuarioNombre;
    private String codigoSede;
    private String nombreSede;
    private String ciudadSede;

    public UsuarioSedeDto() {}

    public UsuarioSedeDto(Long id, String usuarioUsername, String usuarioNombre,
                          String codigoSede, String nombreSede, String ciudadSede) {
        this.id = id;
        this.usuarioUsername = usuarioUsername;
        this.usuarioNombre = usuarioNombre;
        this.codigoSede = codigoSede;
        this.nombreSede = nombreSede;
        this.ciudadSede = ciudadSede;
    }

    // ── Getters / Setters ───────────────────────────────────────────────────

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsuarioUsername() { return usuarioUsername; }
    public void setUsuarioUsername(String usuarioUsername) { this.usuarioUsername = usuarioUsername; }

    public String getUsuarioNombre() { return usuarioNombre; }
    public void setUsuarioNombre(String usuarioNombre) { this.usuarioNombre = usuarioNombre; }

    public String getCodigoSede() { return codigoSede; }
    public void setCodigoSede(String codigoSede) { this.codigoSede = codigoSede; }

    public String getNombreSede() { return nombreSede; }
    public void setNombreSede(String nombreSede) { this.nombreSede = nombreSede; }

    public String getCiudadSede() { return ciudadSede; }
    public void setCiudadSede(String ciudadSede) { this.ciudadSede = ciudadSede; }
}
