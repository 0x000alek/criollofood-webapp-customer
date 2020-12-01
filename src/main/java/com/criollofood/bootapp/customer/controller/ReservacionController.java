package com.criollofood.bootapp.customer.controller;

import com.criollofood.bootapp.customer.domain.Cliente;
import com.criollofood.bootapp.customer.domain.Reservacion;
import com.criollofood.bootapp.customer.service.ClienteService;
import com.criollofood.bootapp.customer.service.ReservacionService;
import com.criollofood.bootapp.customer.util.AuthenticationFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.math.BigDecimal;

@Controller
public class ReservacionController {
    private static final Logger LOGGER = LogManager.getLogger(ReservacionController.class);

    private final AuthenticationFacade authenticationFacade;
    private final ClienteService clienteService;
    private final ReservacionService reservacionService;

    public ReservacionController(@Autowired AuthenticationFacade authenticationFacade,
                                 @Autowired ClienteService clienteService,
                                 @Autowired ReservacionService reservacionService) {
        this.authenticationFacade = authenticationFacade;
        this.clienteService = clienteService;
        this.reservacionService = reservacionService;
    }

    @GetMapping(value = "/reservaciones")
    public ModelAndView reservaciones(HttpSession session) {
        if (!authenticationFacade.isAuthenticated() || !authenticationFacade.hasAuthority("CLIENTE")) {
            return new ModelAndView("redirect:/acceder");
        }
        ModelAndView modelAndView = new ModelAndView();
        Cliente cliente = (Cliente) session.getAttribute("cliente");

        modelAndView.addObject("reservacion", new Reservacion(cliente.getNombre(), cliente.getTelefono()));
        modelAndView.addObject("reservaciones", reservacionService.findByIdCliente(cliente.getId()));
        modelAndView.setViewName("reservaciones");

        return modelAndView;
    }

    @PostMapping(value = "/reservaciones")
    public ModelAndView crearReservacion(@Valid @ModelAttribute("reservacion") Reservacion reservacion,
                                         BindingResult bindingResult,
                                         HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        Cliente cliente = (Cliente) session.getAttribute("cliente");
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("reservaciones", reservacionService.findByIdCliente(cliente.getId()));
            modelAndView.setViewName("reservaciones");
        } else {
            if (cliente.getId().compareTo(BigDecimal.ZERO) == 0) {
                cliente = addCliente(reservacion, cliente.getCorreo());
            }

            session.setAttribute("cliente", cliente);
            reservacionService.add(reservacion, cliente.getId());
            return new ModelAndView("redirect:/reservaciones");
        }

        return modelAndView;
    }

    @GetMapping(value = "/reservaciones/{id}/cancelar")
    public ModelAndView cancelarReservacion(@PathVariable BigDecimal id) {
        reservacionService.cancel(id);
        return new ModelAndView("redirect:/reservaciones");
    }

    private Cliente addCliente(Reservacion reservacion, String correoCliente) {
        Cliente cliente = new Cliente();

        cliente.setNombre(reservacion.getNombreCliente());
        cliente.setTelefono(reservacion.getTelefonoCliente());
        cliente.setCorreo(correoCliente);

        return clienteService.add(cliente);
    }
}
