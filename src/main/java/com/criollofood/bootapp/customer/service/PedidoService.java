package com.criollofood.bootapp.customer.service;

import com.criollofood.bootapp.customer.domain.Atencion;
import com.criollofood.bootapp.customer.domain.Pedido;
import com.criollofood.bootapp.customer.domain.RecetaPedido;
import com.criollofood.bootapp.customer.sql.AgregarRecetaPedidoSP;
import com.criollofood.bootapp.customer.sql.CrearPedidoSP;
import com.criollofood.bootapp.customer.sql.ListarRecetasByIdPedidoSP;
import com.criollofood.bootapp.customer.sql.ObtenerPedidoByIdAtencion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class PedidoService {
    private static final Logger LOGGER = LogManager.getLogger(PedidoService.class);

    private final CrearPedidoSP crearPedidoSP;
    private final AgregarRecetaPedidoSP agregarRecetaPedidoSP;
    private final ObtenerPedidoByIdAtencion obtenerPedidoByIdAtencion;
    private final ListarRecetasByIdPedidoSP listarRecetasByIdPedidoSP;

    private final Map<BigDecimal, RecetaPedido> items = new LinkedHashMap<>(Collections.emptyMap());
    private BigDecimal currentMapKey;

    public PedidoService(@Autowired CrearPedidoSP crearPedidoSP,
                         @Autowired AgregarRecetaPedidoSP agregarRecetaPedidoSP,
                         @Autowired ObtenerPedidoByIdAtencion obtenerPedidoByIdAtencion,
                         @Autowired ListarRecetasByIdPedidoSP listarRecetasByIdPedidoSP) {
        this.crearPedidoSP = crearPedidoSP;
        this.agregarRecetaPedidoSP = agregarRecetaPedidoSP;
        this.obtenerPedidoByIdAtencion = obtenerPedidoByIdAtencion;
        this.listarRecetasByIdPedidoSP = listarRecetasByIdPedidoSP;

        this.currentMapKey = BigDecimal.ZERO;
    }

    public Pedido create(BigDecimal atencionId) {
        BigDecimal pedidoId = crearPedidoSP.execute(atencionId);
        if (Objects.isNull(pedidoId)) {
            return null;
        }

        Map<BigDecimal, RecetaPedido> recetasPedido = new LinkedHashMap<>(Collections.emptyMap());
        items.forEach((k,v) -> {
            v.setPedidoId(pedidoId);
            RecetaPedido recetaPedido = agregarRecetaPedidoSP.execute(v);
            recetasPedido.put(recetaPedido.getId(), recetaPedido);
        });

        items.clear();
        items.putAll(recetasPedido);

        Pedido pedido = new Pedido(pedidoId);
        pedido.setAtencionId(atencionId);

        return pedido;
    }

    public Pedido findByIdAtencion(Atencion atencion) {
        if (Objects.isNull(atencion)) {
            items.clear();
            return null;
        }

        Pedido pedido = obtenerPedidoByIdAtencion.execute(atencion.getId());
        if (Objects.isNull(pedido)) {
            items.clear();
            return null;
        }

        listarRecetasByIdPedidoSP.execute(pedido.getId()).forEach(item -> {
            items.put(item.getId(), item);
        });

        return pedido;
    }

    public Pedido findByIdAtencionOrDefault(Atencion atencion, Pedido pedidoDefault) {
        Pedido pedido = findByIdAtencion(atencion);
        return Objects.isNull(pedido) ? pedidoDefault : pedido;
    }

    public void addItem(RecetaPedido recetaPedido) {
        currentMapKey = currentMapKey.add(BigDecimal.ONE);
        items.put(currentMapKey, recetaPedido);
    }

    public void removeItem(BigDecimal recetaPedidoId) {
        items.remove(recetaPedidoId);
    }

    public Map<BigDecimal, RecetaPedido> getItems() {
        return items;
    }
}
