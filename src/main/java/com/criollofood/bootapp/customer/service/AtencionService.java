package com.criollofood.bootapp.customer.service;

import com.criollofood.bootapp.customer.domain.Atencion;
import com.criollofood.bootapp.customer.sql.ObtenerAtencionByIdClienteSP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

@Service
public class AtencionService {
    private final ObtenerAtencionByIdClienteSP obtenerAtencionByIdClienteSP;

    public AtencionService(@Autowired ObtenerAtencionByIdClienteSP obtenerAtencionByIdClienteSP) {
        this.obtenerAtencionByIdClienteSP = obtenerAtencionByIdClienteSP;
    }

    public Atencion findByIdCliente(BigDecimal clienteId) {
        return obtenerAtencionByIdClienteSP.execute(clienteId);
    }

    public Atencion findByIdClienteOrDefault(BigDecimal clienteId, Atencion atencionDefault) {
        Atencion atencion = findByIdCliente(clienteId);
        return Objects.isNull(atencion) ? atencionDefault : atencion;
    }
}
