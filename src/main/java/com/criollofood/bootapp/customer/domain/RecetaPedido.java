package com.criollofood.bootapp.customer.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class RecetaPedido implements Serializable {
    private static final long serialVersionUID = -2935200989138022432L;

    private BigDecimal id;
    private String comentario;
    private BigDecimal pedidoId;
    private BigDecimal recetaId;
    private String estado;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public BigDecimal getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(BigDecimal pedidoId) {
        this.pedidoId = pedidoId;
    }

    public BigDecimal getRecetaId() {
        return recetaId;
    }

    public void setRecetaId(BigDecimal recetaId) {
        this.recetaId = recetaId;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "RecetaPedido{" +
                "id=" + id +
                ", comentario='" + comentario + '\'' +
                ", pedidoId=" + pedidoId +
                ", recetaId=" + recetaId +
                ", estado='" + estado + '\'' +
                '}';
    }
}
