package com.criollofood.bootapp.customer.controller;

import com.criollofood.bootapp.customer.domain.Atencion;
import com.criollofood.bootapp.customer.domain.Cliente;
import com.criollofood.bootapp.customer.domain.Pedido;
import com.criollofood.bootapp.customer.service.AtencionService;
import com.criollofood.bootapp.customer.service.ClienteService;
import com.criollofood.bootapp.customer.service.PedidoService;
import com.criollofood.bootapp.customer.util.AuthenticationFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Objects;

@Controller
public class LoginController {
    private static final Logger LOGGER = LogManager.getLogger(LoginController.class);

    private final AuthenticationFacade authenticationFacade;
    private final AtencionService atencionService;
    private final ClienteService clienteService;
    private final PedidoService pedidoService;

    public LoginController(@Autowired AuthenticationFacade authenticationFacade,
                           @Autowired AtencionService atencionService,
                           @Autowired ClienteService clienteService,
                           @Autowired PedidoService pedidoService) {
        this.authenticationFacade = authenticationFacade;
        this.atencionService = atencionService;
        this.clienteService = clienteService;
        this.pedidoService = pedidoService;
    }

    @GetMapping(value = "/acceder")
    public ModelAndView acceder() {
        ModelAndView modelAndView = new ModelAndView();
        if (authenticationFacade.isAuthenticated()) {
            return new ModelAndView("redirect:/reservaciones");
        }
        modelAndView.addObject("cliente", new Cliente());
        modelAndView.setViewName("login");

        return modelAndView;
    }

    @PostMapping(value = "/acceder")
    public ModelAndView acceder(@Valid @ModelAttribute("cliente") Cliente cliente,
                                 BindingResult bindingResult,
                                 HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("login");
        } else {
            String correoCliente = cliente.getCorreo();
            authenticationFacade.authWithoutPassword(correoCliente);

            cliente = clienteService.findByCorreoOrDefault(correoCliente, new Cliente(correoCliente));
            Atencion atencion = atencionService.findByIdCliente(cliente.getId());

            session.setAttribute("cliente", cliente);
            session.setAttribute("atencion", atencion);
            session.setAttribute("pedido", pedidoService.findByIdAtencion(atencion));
            session.setAttribute("itemsPedido", pedidoService.getItems());

            return new ModelAndView("redirect:/reservaciones");
        }

        return modelAndView;
    }
}
