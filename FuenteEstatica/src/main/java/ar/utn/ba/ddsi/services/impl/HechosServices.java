package ar.utn.ba.ddsi.services.impl;

import ar.utn.ba.ddsi.models.dtos.HechoInputDTO;
import ar.utn.ba.ddsi.models.dtos.HechoOutputDTO;
import ar.utn.ba.ddsi.models.entities.hecho.Categoria;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.models.entities.importador.Importador;
import ar.utn.ba.ddsi.models.entities.importador.ImportadorCSV;
import ar.utn.ba.ddsi.models.repositories.ICategoriaRepository;
import ar.utn.ba.ddsi.models.repositories.IHechosRepository;
import ar.utn.ba.ddsi.services.IHechosServices;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import static ar.utn.ba.ddsi.conversores.HechoMapper.*;

@Service
public class HechosServices implements IHechosServices {

    @Autowired
    private IHechosRepository repositorioDeHechos;
    private Importador importadorCSV = new ImportadorCSV();
    @Autowired
    private ICategoriaRepository categoriaRepository;

    @Override
    public void importarHechos(List<String> unosArchivos) throws IOException, CsvValidationException {
        for (String archivoIndice : unosArchivos) {
            for (HechoInputDTO hechoInputIndice : importadorCSV.importarHechos(archivoIndice)) {
                Hecho hechoConvertido = this.inputDTOAHecho(hechoInputIndice);
                this.repositorioDeHechos.save(hechoConvertido);
            }
        }
    }

    public Hecho inputDTOAHecho(HechoInputDTO unHechoInput) {
        Hecho hecho = toHecho(unHechoInput);

        if (unHechoInput.getCategoria() != null && !unHechoInput.getCategoria().isBlank()) {
            String nombreCategoria = unHechoInput.getCategoria().trim();

            Categoria categoriaPersistida = categoriaRepository
                    .findByNombre(nombreCategoria)
                    .orElseGet(() -> {
                        Categoria nueva = new Categoria();
                        nueva.setNombre(nombreCategoria);
                        return categoriaRepository.save(nueva);
                    });

            hecho.setCategoria(categoriaPersistida);
        }
        return hecho;
    }

    @Override
    public List<HechoOutputDTO> getHechos() {
        return repositorioDeHechos.findAll()
                .stream()
                .map(this::hechoOutputDTO)
                .collect(Collectors.toList());
    }

    public HechoOutputDTO hechoOutputDTO(Hecho hecho) {
        return toOutputDTO(hecho);
    }

}