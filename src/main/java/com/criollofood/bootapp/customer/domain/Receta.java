package com.criollofood.bootapp.customer.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class Receta implements Serializable {
    private static final long serialVersionUID = -8131421545195676956L;

    private BigDecimal id;
    private String nombre;
    private String descripcion;
    private Timestamp fechaIngreso;
    private BigDecimal precio;
    private boolean estaDisponible;
    private String imagen;
    private BigDecimal categoriaId;

    private Integer cantidad;

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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Timestamp getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Timestamp fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public boolean isEstaDisponible() {
        return estaDisponible;
    }

    public void setEstaDisponible(boolean estaDisponible) {
        this.estaDisponible = estaDisponible;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(BigDecimal categoriaId) {
        this.categoriaId = categoriaId;
    }

    @Override
    public String toString() {
        return "Receta{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fechaIngreso=" + fechaIngreso +
                ", precio=" + precio +
                ", estaDisponible=" + estaDisponible +
                ", imagen='" + imagen + '\'' +
                ", categoriaId=" + categoriaId +
                ", cantidad=" + cantidad +
                '}';
    }
}
