package com.criollofood.bootapp.customer.service;

import com.criollofood.bootapp.customer.domain.Receta;
import com.criollofood.bootapp.customer.sql.ListarRecetasDisponibles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecetaService {
    private final ListarRecetasDisponibles listarRecetasDisponibles;
    private final Map<BigDecimal, Receta> recetas = new LinkedHashMap<>(Collections.emptyMap());

    private final CategoriaService categoriaService;

    public RecetaService(@Autowired ListarRecetasDisponibles listarRecetasDisponibles,
                         @Autowired CategoriaService categoriaService) {
        this.listarRecetasDisponibles = listarRecetasDisponibles;
        this.categoriaService = categoriaService;
    }

    @PostConstruct
    private void init() {
        findAllDisponibles().forEach(i -> {
            recetas.put(i.getId(), i);
        });
    }

    public List<Receta> findAllDisponibles() {
        return listarRecetasDisponibles.execute();
    }

    public Map<BigDecimal, Receta> getRecetas() {
        return recetas;
    }

    public Map<BigDecimal, List<Receta>> getRecetasByCategoria() {
        Map<BigDecimal, List<Receta>> recetasByCategoria = new LinkedHashMap<>(Collections.emptyMap());
        categoriaService.getCategoriasEnCarta().forEach(c -> {
            recetasByCategoria.put(c.getId(), recetas.values()
                    .stream()
                    .filter(r -> r.getCategoriaId().compareTo(c.getId()) == 0)
                    .collect(Collectors.toList()));
        });

        return recetasByCategoria;
    }
}
