package ar.utn.ba.ddsi.gateway.controllers;


import ar.utn.ba.ddsi.gateway.models.dtos.output.CategoriaOutputDTO;
import ar.utn.ba.ddsi.gateway.services.coleccionService.algoritmoConsenso.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriasController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping()
    public List<CategoriaOutputDTO> getAllCategorias(){
        return categoriaService.buscarCategorias();
    }

    @GetMapping("/{id}")
    public CategoriaOutputDTO getCategoria(Long id){
        return categoriaService.buscarCategoriaPorId(id);
    }
}
