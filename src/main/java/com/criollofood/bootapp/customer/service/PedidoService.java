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
        Pedido pedido = crearPedidoSP.execute(atencionId);
        if (Objects.isNull(pedido)) {
            return null;
        }

        Map<BigDecimal, RecetaPedido> recetasPedido = new LinkedHashMap<>(Collections.emptyMap());
        items.forEach((k,v) -> {
            v.setPedidoId(pedido.getId());
            RecetaPedido recetaPedido = agregarRecetaPedidoSP.execute(v);
            recetasPedido.put(recetaPedido.getId(), recetaPedido);
        });

        items.clear();
        items.putAll(recetasPedido);

        return pedido;
    }

    public Pedido findByAtencion(Atencion atencion) {
        return Objects.isNull(atencion) ? null : obtenerPedidoByIdAtencion.execute(atencion.getId());
    }

    public Pedido findByAtencionOrDefault(Atencion atencion, Pedido pedidoDefault) {
        Pedido pedido = findByAtencion(atencion);
        return Objects.isNull(pedido) ? pedidoDefault : pedido;
    }

    public Map<BigDecimal, RecetaPedido> findRecetasByPedido(Pedido pedido) {
        if (!Objects.isNull(pedido)) {
            listarRecetasByIdPedidoSP.execute(pedido.getId()).forEach(item -> {
                items.put(item.getId(), item);
            });
        }
        return items;
    }

    public Map<BigDecimal, RecetaPedido> findRecetasCuentaByPedido(Pedido pedido) {
        Map<BigDecimal, RecetaPedido> recetasCuenta = new LinkedHashMap<>(Collections.emptyMap());
        if (!Objects.isNull(pedido)) {
            listarRecetasByIdPedidoSP.execute(pedido.getId()).forEach(item -> {
                recetasCuenta.put(item.getId(), item);
            });
        }
        return recetasCuenta;
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
