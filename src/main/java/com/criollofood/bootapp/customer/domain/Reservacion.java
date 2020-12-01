package com.criollofood.bootapp.customer.domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Reservacion implements Serializable {
    private static final long serialVersionUID = -5481450373388769671L;

    private BigDecimal id;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @NotNull(message = "*Porfavor ingresa una fecha")
    private Date fecha;
    @Min(value = 1, message = "*El mínimo permitido es de 1 persona")
    @Max(value = 20, message = "*El máximo permitido es de 20 personas")
    private int asistentes;
    private String codigo;
    private String estado;

    @NotEmpty(message = "*Porfavor ingresa a nombre de quien se hace la reservación")
    private String nombreCliente;
    private String telefonoCliente;

    public Reservacion() {
    }

    public Reservacion(String nombreCliente, String telefonoCliente) {
        this.nombreCliente = nombreCliente;
        this.telefonoCliente = telefonoCliente;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getAsistentes() {
        return asistentes;
    }

    public void setAsistentes(int asistentes) {
        this.asistentes = asistentes;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getTelefonoCliente() {
        return telefonoCliente;
    }

    public void setTelefonoCliente(String telefonoCliente) {
        this.telefonoCliente = telefonoCliente;
    }

    @Override
    public String toString() {
        return "Reservacion{" +
                "id=" + id +
                ", fecha=" + fecha +
                ", asistentes=" + asistentes +
                ", codigo='" + codigo + '\'' +
                ", estado='" + estado + '\'' +
                ", nombreCliente='" + nombreCliente + '\'' +
                ", telefonoCliente='" + telefonoCliente + '\'' +
                '}';
    }
}
