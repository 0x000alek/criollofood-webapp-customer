package com.criollofood.bootapp.customer.controller;

import com.criollofood.bootapp.customer.service.CategoriaService;
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
    private final CategoriaService categoriaService;
    private final RecetaService recetaService;

    public HomeController(@Autowired AuthenticationFacade authenticationFacade,
                          @Autowired CategoriaService categoriaService,
                          @Autowired RecetaService recetaService) {
        this.authenticationFacade = authenticationFacade;
        this.categoriaService = categoriaService;
        this.recetaService = recetaService;
    }

    @GetMapping(value= "/")
    public ModelAndView home(HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        if (authenticationFacade.isAuthenticated()) {
            modelAndView.addObject("atencion", session.getAttribute("atencion"));
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
