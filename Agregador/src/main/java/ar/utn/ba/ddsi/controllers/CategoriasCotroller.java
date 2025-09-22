package ar.utn.ba.ddsi.controllers;


import ar.utn.ba.ddsi.models.entities.hecho.Categoria;
import ar.utn.ba.ddsi.services.coleccionService.algoritmoConsenso.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriasCotroller {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/all")
    public List<Categoria> getAllCategorias(){
        return categoriaService.buscarCategorias();
    }

    @GetMapping("/{id}")
    public Categoria getCategoria(Long id){
        return categoriaService.buscarCategoriaPorId(id);
    }
}
