package com.criollofood.bootapp.customer.controller;

import com.criollofood.bootapp.customer.domain.*;
import com.criollofood.bootapp.customer.service.AtencionService;
import com.criollofood.bootapp.customer.service.PedidoService;
import com.criollofood.bootapp.customer.service.RecetaService;
import com.criollofood.bootapp.customer.util.AuthenticationFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

@Controller
public class CuentaController {
    private static final Logger LOGGER = LogManager.getLogger(CuentaController.class);

    private final AuthenticationFacade authenticationFacade;
    private final AtencionService atencionService;
    private final PedidoService pedidoService;
    private final RecetaService recetaService;

    public CuentaController(@Autowired AuthenticationFacade authenticationFacade,
                            @Autowired AtencionService atencionService,
                            @Autowired PedidoService pedidoService,
                            @Autowired RecetaService recetaService) {
        this.authenticationFacade = authenticationFacade;
        this.atencionService = atencionService;
        this.pedidoService = pedidoService;
        this.recetaService = recetaService;
    }

    @GetMapping(value = "/cuenta")
    public ModelAndView cuenta(HttpSession session) {
        if (!authenticationFacade.isAuthenticated()) {
            return new ModelAndView("redirect:/acceder");
        }
        ModelAndView modelAndView = new ModelAndView();

        Cliente cliente = (Cliente) session.getAttribute("cliente");
        Atencion atencion = atencionService.findByIdCliente(cliente.getId());
        Pedido pedido = pedidoService.findByAtencion(atencion);

        Map<BigDecimal, RecetaPedido> itemsPedido = pedidoService.findRecetasCuentaByPedido(pedido);
        Map<BigDecimal, Receta> recetas = recetaService.getRecetas();

        BigDecimal monto = BigDecimal.ZERO;

        for (RecetaPedido recetaPedido : itemsPedido.values()) {
            Receta receta = recetas.get(recetaPedido.getRecetaId());
            monto = monto.add(Objects.isNull(receta) ? BigDecimal.ZERO : receta.getPrecio());
        }

        int servicio = monto.multiply(BigDecimal.valueOf(0.1)).intValue();
        BigDecimal total = monto.add(BigDecimal.valueOf(servicio));

        modelAndView.addObject("itemsPedido", itemsPedido);
        modelAndView.addObject("monto", monto);
        modelAndView.addObject("servicio", servicio);
        modelAndView.addObject("total", total);
        modelAndView.addObject("recetas", recetas);
        modelAndView.setViewName("cuenta");

        return modelAndView;
    }
}
