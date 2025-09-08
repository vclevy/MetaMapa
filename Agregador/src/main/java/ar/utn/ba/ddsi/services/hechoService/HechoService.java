package ar.utn.ba.ddsi.services.hechoService;

import ar.utn.ba.ddsi.models.entities.hecho.Categoria;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.models.repositories.ICategoriasRepository;
import ar.utn.ba.ddsi.models.repositories.IHechosRepository;
import ar.utn.ba.ddsi.services.georef.LugarService;
import ar.utn.ba.ddsi.services.hechoService.normalizador.NormalizadorHechos;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;

@Service
public class HechoService implements IHechoService {
    @Autowired
    private IHechosRepository hechosRepository;
    @Autowired
    private ICategoriasRepository categoriaRepository;
    @Autowired
    private NormalizadorHechos normalizadorHechos;

    @Override
    public void registrarHechoDesdeFuente(Hecho unHecho) {
        Hecho hechoPosta = normalizadorHechos.normalizar(unHecho);

        if (hechoPosta.getCategoria() == null ||
                hechoPosta.getCategoria().getNombre() == null ||
                hechoPosta.getCategoria().getNombre().isBlank()) {
            throw new IllegalArgumentException("El hecho debe tener una categoría válida");
        }

        String nombreCategoria = hechoPosta.getCategoria().getNombre().trim();
        Categoria categoriaPersistida = categoriaRepository.findByNombre(nombreCategoria)
                .orElseGet(() -> categoriaRepository.saveAndFlush(new Categoria(nombreCategoria)));

        hechoPosta.setCategoria(categoriaPersistida);
        hechosRepository.saveAndFlush(hechoPosta);
    }






}
