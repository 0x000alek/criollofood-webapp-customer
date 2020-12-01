package com.criollofood.bootapp.customer.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class Categoria implements Serializable {
    private static final long serialVersionUID = -2929951505848690202L;

    private BigDecimal id;
    private String nombre;
    private boolean estaEnCarta;

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

    public boolean isEstaEnCarta() {
        return estaEnCarta;
    }

    public void setEstaEnCarta(boolean estaEnCarta) {
        this.estaEnCarta = estaEnCarta;
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", estaEnCarta=" + estaEnCarta +
                '}';
    }
}
