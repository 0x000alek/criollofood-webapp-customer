package com.criollofood.bootapp.customer.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Pedido implements Serializable {
    private static final long serialVersionUID = -3570071124220645325L;

    private BigDecimal id;
    private String estado;
    private Date fechaIngreso;
    private Date fechaPreparacion;
    private Date fechaEntrega;
    private BigDecimal atencionId;

    public Pedido() {
    }

    public Pedido(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Date getFechaPreparacion() {
        return fechaPreparacion;
    }

    public void setFechaPreparacion(Date fechaPreparacion) {
        this.fechaPreparacion = fechaPreparacion;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public BigDecimal getAtencionId() {
        return atencionId;
    }

    public void setAtencionId(BigDecimal atencionId) {
        this.atencionId = atencionId;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", estado='" + estado + '\'' +
                ", fechaIngreso=" + fechaIngreso +
                ", fechaPreparacion=" + fechaPreparacion +
                ", fechaEntrega=" + fechaEntrega +
                ", atencionId=" + atencionId +
                '}';
    }
}
