package com.inventory.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "usuarios")
public class User implements UserDetails {
    @Id
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = true)
    private String firstName;

    @Column(nullable = true)
    private String lastName;

    /** Número de identificación del empleado (cédula, NIT, etc.) */
    @Column(length = 30, nullable = true)
    private String cedula;

    @Column(nullable = true)
    private String email;

    @Column(nullable = true)
    private String telefono;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roles_name", nullable = false)
    private Rol role;

    @Basic(fetch = FetchType.LAZY)
    @Column(nullable = true) // El campo es opcional
    private byte[] profilePicture;

    /** Indica si el usuario puede iniciar sesión. false = cuenta suspendida. */
    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean activo = true;

    /** Indica si la cuenta está bloqueada (p.ej. por intentos fallidos). */
    @Column(name = "cuenta_bloqueada", nullable = false, columnDefinition = "boolean default false")
    private boolean cuentaBloqueada = false;

    @Column(name = "fecha_creacion", nullable = true, updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "ultimo_login", nullable = true)
    private LocalDateTime ultimoLogin;

    @PrePersist
    protected void onCreate() {
        if (this.fechaCreacion == null) this.fechaCreacion = LocalDateTime.now();
    }

    // Constructor
    public User() {
    }

    public User(String username, String password, Rol role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public User(String username, String password, Rol role, byte[] profilePicture) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.profilePicture = profilePicture;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Rol getRole() {
        return role;
    }

    public void setRole(Rol role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCedula() { return cedula; }
    public void setCedula(String cedula) { this.cedula = cedula; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public boolean isCuentaBloqueada() { return cuentaBloqueada; }
    public void setCuentaBloqueada(boolean cuentaBloqueada) { this.cuentaBloqueada = cuentaBloqueada; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public LocalDateTime getUltimoLogin() { return ultimoLogin; }
    public void setUltimoLogin(LocalDateTime ultimoLogin) { this.ultimoLogin = ultimoLogin; }

    @Override
    public String toString() {
        return "Usuarios [username=" + username + ", role=" + role + "]";
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Asegúrate de que el rol no sea nulo antes de acceder a su nombre
        if (this.role != null && this.role.getName() != null) {
            return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + this.role.getName()));
        }
        // Si el usuario no tiene rol o el nombre del rol es nulo, devuelve una lista vacía de autoridades.
        // Considera si un usuario debería siempre tener un rol para tu aplicación.
        return Collections.emptyList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Implementa tu lógica de expiración de cuenta si es necesario
    }

    @Override
    public boolean isAccountNonLocked() {
        return !cuentaBloqueada;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return activo;
    }
}
