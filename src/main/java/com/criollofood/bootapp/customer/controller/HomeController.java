package com.criollofood.bootapp.customer.controller;

import com.criollofood.bootapp.customer.domain.Atencion;
import com.criollofood.bootapp.customer.domain.Cliente;
import com.criollofood.bootapp.customer.service.AtencionService;
import com.criollofood.bootapp.customer.service.CategoriaService;
import com.criollofood.bootapp.customer.service.PedidoService;
import com.criollofood.bootapp.customer.service.RecetaService;
import com.criollofood.bootapp.customer.util.AuthenticationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {
    private final AuthenticationFacade authenticationFacade;
    private final AtencionService atencionService;
    private final CategoriaService categoriaService;
    private final PedidoService pedidoService;
    private final RecetaService recetaService;

    public HomeController(@Autowired AuthenticationFacade authenticationFacade,
                          @Autowired AtencionService atencionService,
                          @Autowired CategoriaService categoriaService,
                          @Autowired PedidoService pedidoService,
                          @Autowired RecetaService recetaService) {
        this.authenticationFacade = authenticationFacade;
        this.atencionService = atencionService;
        this.categoriaService = categoriaService;
        this.pedidoService = pedidoService;
        this.recetaService = recetaService;
    }

    @GetMapping(value= "/")
    public ModelAndView home(HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        if (authenticationFacade.isAuthenticated()) {
            Cliente cliente = (Cliente) session.getAttribute("cliente");
            Atencion atencion = atencionService.findByIdCliente(cliente.getId());

            modelAndView.addObject("atencion", atencion);
            modelAndView.addObject("pedido", pedidoService.findByAtencion(atencion));
        }
        modelAndView.addObject("categorias", categoriaService.getCategoriasEnCarta());
        modelAndView.addObject("recetasByCategoria", recetaService.getRecetasByCategoria());
        modelAndView.setViewName("index");

        return modelAndView;
    }

    @GetMapping(value= "/index")
    public ModelAndView index(){
        return new ModelAndView("redirect:/");
    }
}
