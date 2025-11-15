package ar.utn.ba.ddsi.gateway.services.coleccionService.algoritmoConsenso;

import ar.utn.ba.ddsi.gateway.models.dtos.output.CategoriaOutputDTO;
import ar.utn.ba.ddsi.gateway.models.repositories.ICategoriasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private ICategoriasRepository categoriasRepository;

    public List<CategoriaOutputDTO> buscarCategorias() {
        return categoriasRepository.findAll().stream()
                .map(CategoriaOutputDTO::new)
                .toList();

    }

    public CategoriaOutputDTO buscarCategoriaPorId(Long id) {
        return categoriasRepository.findById(id)
                .map(CategoriaOutputDTO::new)
                .orElse(null);
    }

}
