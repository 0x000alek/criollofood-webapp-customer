package com.criollofood.bootapp.customer.service;

import com.criollofood.bootapp.customer.domain.Atencion;
import com.criollofood.bootapp.customer.domain.Pedido;
import com.criollofood.bootapp.customer.domain.RecetaPedido;
import com.criollofood.bootapp.customer.sql.AgregarRecetaPedidoSP;
import com.criollofood.bootapp.customer.sql.CrearPedidoSP;
import com.criollofood.bootapp.customer.sql.ObtenerPedidoByIdAtencion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class PedidoService {
    private final CrearPedidoSP crearPedidoSP;
    private final AgregarRecetaPedidoSP agregarRecetaPedidoSP;
    private final ObtenerPedidoByIdAtencion obtenerPedidoByIdAtencion;

    private final Map<BigDecimal, RecetaPedido> items = new LinkedHashMap<>(Collections.emptyMap());
    private BigDecimal currentMapKey;

    public PedidoService(@Autowired CrearPedidoSP crearPedidoSP,
                         @Autowired AgregarRecetaPedidoSP agregarRecetaPedidoSP,
                         @Autowired ObtenerPedidoByIdAtencion obtenerPedidoByIdAtencion) {
        this.crearPedidoSP = crearPedidoSP;
        this.agregarRecetaPedidoSP = agregarRecetaPedidoSP;
        this.obtenerPedidoByIdAtencion = obtenerPedidoByIdAtencion;

        this.currentMapKey = BigDecimal.ZERO;
    }

    public Pedido create(BigDecimal atencionId) {
        BigDecimal pedidoId = crearPedidoSP.execute(atencionId);
        if (Objects.isNull(pedidoId)) {
            return null;
        }
        items.forEach((k,v) -> {
            v.setPedidoId(pedidoId);
            agregarRecetaPedidoSP.execute(v);
        });

        Pedido pedido = new Pedido(pedidoId);
        pedido.setAtencionId(atencionId);

        return pedido;
    }

    public Pedido findByIdAtencion(Atencion atencion) {
        return Objects.isNull(atencion) ? null : obtenerPedidoByIdAtencion.execute(atencion.getId());
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
