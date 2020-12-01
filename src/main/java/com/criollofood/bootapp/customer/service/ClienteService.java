package com.criollofood.bootapp.customer.service;

import com.criollofood.bootapp.customer.domain.Cliente;
import com.criollofood.bootapp.customer.sql.CrearClienteSP;
import com.criollofood.bootapp.customer.sql.ObtenerClienteByCorreo;
import com.criollofood.bootapp.customer.util.AESEncrypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ClienteService {
    private final AESEncrypter aesEncrypter;

    private final CrearClienteSP crearClienteSP;
    private final ObtenerClienteByCorreo obtenerClienteByCorreo;

    public ClienteService(@Autowired AESEncrypter aesEncrypter,
                          @Autowired CrearClienteSP crearClienteSP,
                          @Autowired ObtenerClienteByCorreo obtenerClienteByCorreo) {
        this.aesEncrypter = aesEncrypter;

        this.crearClienteSP = crearClienteSP;
        this.obtenerClienteByCorreo = obtenerClienteByCorreo;
    }

    public Cliente add(Cliente cliente) {
        cliente.setCorreo(aesEncrypter.encrypt(cliente.getCorreo()));
        return stage(crearClienteSP.execute(cliente));
    }

    public Cliente findByCorreo(String clienteCorreo) {
        return stage(obtenerClienteByCorreo.execute(aesEncrypter.encrypt(clienteCorreo)));
    }

    public Cliente findByCorreoOrDefault(String clienteCorreo, Cliente clienteDefault) {
        Cliente cliente = findByCorreo(clienteCorreo);
        return Objects.isNull(cliente) ? clienteDefault : cliente;
    }

    private Cliente stage(Cliente cliente) {
        if (!Objects.isNull(cliente)) {
            cliente.setCorreo(aesEncrypter.decrypt(cliente.getCorreo()));
        }
        return cliente;
    }
}
