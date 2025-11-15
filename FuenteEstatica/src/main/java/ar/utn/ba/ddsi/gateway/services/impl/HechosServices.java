package ar.utn.ba.ddsi.gateway.services.impl;

import ar.utn.ba.ddsi.gateway.conversores.HechoMapper;
import ar.utn.ba.ddsi.gateway.models.dtos.HechoInputDTO;
import ar.utn.ba.ddsi.gateway.models.dtos.HechoOutputDTO;
import ar.utn.ba.ddsi.gateway.models.entities.hecho.Categoria;
import ar.utn.ba.ddsi.gateway.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.gateway.models.entities.importador.Importador;
import ar.utn.ba.ddsi.gateway.models.entities.importador.ImportadorCSV;
import ar.utn.ba.ddsi.gateway.models.repositories.ICategoriaRepository;
import ar.utn.ba.ddsi.gateway.models.repositories.IHechosRepository;
import ar.utn.ba.ddsi.gateway.normalizador.NormalizadorHechos;
import ar.utn.ba.ddsi.gateway.services.IHechosServices;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;
import static ar.utn.ba.ddsi.gateway.conversores.HechoMapper.*;

@Service
public class HechosServices implements IHechosServices {

    @Autowired
    private IHechosRepository repositorioDeHechos;
    private Importador importadorCSV = new ImportadorCSV();
    @Autowired
    private ICategoriaRepository categoriaRepository;
    @Autowired
    private HechoMapper hechoMapper;
    @Autowired
    private NormalizadorHechos normalizadorHechos;

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

        hecho = normalizadorHechos.normalizar(hecho);

        if (hecho.getCategoria() != null && !hecho.getCategoria().getNombre().isBlank()) {
            String nombreCategoria = hecho.getCategoria().getNombre().trim();

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
                .map(hechoMapper::toOutputDTO)
                .toList();
    }
}