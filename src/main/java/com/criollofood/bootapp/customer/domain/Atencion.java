package com.criollofood.bootapp.customer.domain;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

public class Atencion implements Serializable {
    private static final long serialVersionUID = -4724364736958768971L;

    private BigDecimal id;
    private String codigo;
    private boolean estaActivo;
    private boolean estaPagada;
    @NotNull
    private BigDecimal clienteId;
    @NotNull
    private BigDecimal numeroMesa;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public boolean isEstaActivo() {
        return estaActivo;
    }

    public void setEstaActivo(boolean estaActivo) {
        this.estaActivo = estaActivo;
    }

    public boolean isEstaPagada() {
        return estaPagada;
    }

    public void setEstaPagada(boolean estaPagada) {
        this.estaPagada = estaPagada;
    }

    public BigDecimal getClienteId() {
        return clienteId;
    }

    public void setClienteId(BigDecimal clienteId) {
        this.clienteId = clienteId;
    }

    public BigDecimal getNumeroMesa() {
        return numeroMesa;
    }

    public void setNumeroMesa(BigDecimal numeroMesa) {
        this.numeroMesa = numeroMesa;
    }

    @Override
    public String toString() {
        return "Atencion{" +
                "id=" + id +
                ", codigo='" + codigo + '\'' +
                ", estaActivo=" + estaActivo +
                ", estaPagada=" + estaPagada +
                ", clienteId=" + clienteId +
                ", numeroMesa=" + numeroMesa +
                '}';
    }
}
