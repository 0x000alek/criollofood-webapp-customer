package com.criollofood.bootapp.customer.service;

import com.criollofood.bootapp.customer.domain.Reservacion;
import com.criollofood.bootapp.customer.sql.CambiarEstadoReservacionSP;
import com.criollofood.bootapp.customer.sql.CrearReservacionSP;
import com.criollofood.bootapp.customer.sql.ListarReservacionesByIdCliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ReservacionService {
    private final CrearReservacionSP crearReservacionSP;
    private final CambiarEstadoReservacionSP cambiarEstadoReservacionSP;
    private final ListarReservacionesByIdCliente listarReservacionesByIdCliente;

    public ReservacionService(@Autowired CrearReservacionSP crearReservacionSP,
                              @Autowired CambiarEstadoReservacionSP cambiarEstadoReservacionSP,
                              @Autowired ListarReservacionesByIdCliente listarReservacionesByIdCliente) {
        this.crearReservacionSP = crearReservacionSP;
        this.cambiarEstadoReservacionSP = cambiarEstadoReservacionSP;
        this.listarReservacionesByIdCliente = listarReservacionesByIdCliente;
    }

    public boolean add(Reservacion reservacion, BigDecimal clienteId) {
        return crearReservacionSP.execute(reservacion, clienteId);
    }

    public boolean cancel(BigDecimal reservacionId) {
        return cambiarEstadoReservacionSP.execute(reservacionId, "CANCELADA");
    }

    public List<Reservacion> findByIdCliente(BigDecimal clienteId) {
        return listarReservacionesByIdCliente.execute(clienteId);
    }
}
