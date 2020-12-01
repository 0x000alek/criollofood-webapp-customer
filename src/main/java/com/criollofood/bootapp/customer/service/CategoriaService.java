package com.criollofood.bootapp.customer.service;

import com.criollofood.bootapp.customer.domain.Categoria;
import com.criollofood.bootapp.customer.sql.ListarCategoriasSP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaService {
    private final ListarCategoriasSP listarCategoriasSP;

    private final List<Categoria> categorias = new LinkedList<>(Collections.emptyList());

    public CategoriaService(@Autowired ListarCategoriasSP listarCategoriasSP) {
        this.listarCategoriasSP = listarCategoriasSP;
    }

    @PostConstruct
    private void init() {
        categorias.addAll(findAll());
    }

    public List<Categoria> findAll() {
        return listarCategoriasSP.execute();
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public List<Categoria> getCategoriasEnCarta() {
        return categorias.stream()
                .filter(Categoria::isEstaEnCarta)
                .collect(Collectors.toList());
    }
}
