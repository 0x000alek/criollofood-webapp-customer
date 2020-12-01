package com.criollofood.bootapp.customer.controller;

import com.criollofood.bootapp.customer.domain.Atencion;
import com.criollofood.bootapp.customer.domain.Pedido;
import com.criollofood.bootapp.customer.domain.RecetaPedido;
import com.criollofood.bootapp.customer.service.PedidoService;
import com.criollofood.bootapp.customer.service.RecetaService;
import com.criollofood.bootapp.customer.util.AuthenticationFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Objects;

@Controller
public class PedidoController {
    private static final Logger LOGGER = LogManager.getLogger(PedidoController.class);

    private final AuthenticationFacade authenticationFacade;
    private final PedidoService pedidoService;
    private final RecetaService recetaService;

    public PedidoController(@Autowired AuthenticationFacade authenticationFacade,
                            @Autowired PedidoService pedidoService,
                            @Autowired RecetaService recetaService) {
        this.authenticationFacade = authenticationFacade;
        this.pedidoService = pedidoService;
        this.recetaService = recetaService;
    }

    @GetMapping(value = "/pedido")
    public ModelAndView pedido(HttpSession session) {
        if (!authenticationFacade.isAuthenticated()) {
            return new ModelAndView("redirect:/acceder");
        }
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("atencion", (Atencion) session.getAttribute("atencion"));
        modelAndView.addObject("pedido", (Pedido) session.getAttribute("pedido"));
        modelAndView.addObject("itemsPedido", pedidoService.getItems());
        modelAndView.addObject("recetas", recetaService.getRecetas());
        modelAndView.setViewName("pedido");

        return modelAndView;
    }

    @PostMapping(value = "/pedido")
    public ModelAndView confirmar(@RequestParam("atencion-id") BigDecimal atencionId,
                                  HttpSession session) {
        session.setAttribute("pedido", pedidoService.create(atencionId));
        return new ModelAndView("redirect:/pedido");
    }

    @PostMapping(value = "/pedido/agregar")
    public ModelAndView agregar(@RequestParam("receta-id") BigDecimal recetaId,
                                @RequestParam("receta-comentario") String recetaComentario,
                                HttpSession session) {
        Pedido pedido = (Pedido) session.getAttribute("pedido");
        RecetaPedido recetaPedido = new RecetaPedido();

        if (!Objects.isNull(pedido)) {
            recetaPedido.setPedidoId(pedido.getId());
        }
        recetaPedido.setRecetaId(recetaId);
        recetaPedido.setComentario(recetaComentario);

        pedidoService.addItem(recetaPedido);

        session.setAttribute("itemsPedido", pedidoService.getItems());

        return new ModelAndView("redirect:/");
    }

    @GetMapping(value = "/pedido/items/{id}/quitar")
    public ModelAndView quitarDelPedido(@PathVariable("id") BigDecimal recetaPedidoId,
                                        HttpSession session) {
        pedidoService.removeItem(recetaPedidoId);
        session.setAttribute("itemsPedido", pedidoService.getItems());

        return new ModelAndView("redirect:/pedido");
    }
}
