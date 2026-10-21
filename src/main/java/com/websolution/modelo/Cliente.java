package com.websolution.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Entidad Cliente.
 *
 * Representa la tabla clientes de la base de datos websolution.
 * Hibernate utiliza esta clase para realizar el mapeo objeto-relacional.
 */
@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Integer id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Email(message = "Ingrese un correo electrónico válido")
    @Size(max = 150, message = "El correo no puede superar los 150 caracteres")
    @Column(name = "email")
    private String email;

    @Pattern(
        regexp = "\\d{10}",
        message = "El teléfono debe contener exactamente 10 números"
    )
    @Column(name = "telefono")
    private String telefono;

    @Size(max = 100, message = "La empresa no puede superar los 100 caracteres")
    @Column(name = "empresa")
    private String empresa;

    /**
     * Constructor vacío requerido por JPA.
     */
    public Cliente() {
    }

    /**
     * Constructor para crear un cliente sin especificar el ID.
     */
    public Cliente(String nombre, String email, String telefono, String empresa) {
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.empresa = empresa;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }
}