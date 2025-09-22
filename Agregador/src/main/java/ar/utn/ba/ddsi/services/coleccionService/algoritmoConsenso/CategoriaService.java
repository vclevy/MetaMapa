package ar.utn.ba.ddsi.services.coleccionService.algoritmoConsenso;

import ar.utn.ba.ddsi.models.entities.hecho.Categoria;
import ar.utn.ba.ddsi.models.repositories.ICategoriasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private ICategoriasRepository categoriasRepository;

    public List<Categoria> buscarCategorias() {
        return categoriasRepository.findAll();
    }
    public Categoria buscarCategoriaPorId(Long id) {
        return categoriasRepository.findById(id).orElse(null);
    }
}
