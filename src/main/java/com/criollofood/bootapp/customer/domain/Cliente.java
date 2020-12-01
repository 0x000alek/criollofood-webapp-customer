package com.criollofood.bootapp.customer.domain;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.math.BigDecimal;

public class Cliente implements Serializable {
    private static final long serialVersionUID = 5418503486104500521L;

    private BigDecimal id;
    private String nombre;
    private String telefono;
    @NotEmpty(message = "*Porfavor ingresa tu email")
    @Email(message = "*Porfavor ingresa un email válido")
    private String correo;

    public Cliente() {
        this.id = BigDecimal.ZERO;
    }

    public Cliente(String correo) {
        this.id = BigDecimal.ZERO;
        this.correo = correo;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                ", correo='" + correo + '\'' +
                '}';
    }
}
