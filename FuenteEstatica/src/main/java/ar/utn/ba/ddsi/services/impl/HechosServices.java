package ar.utn.ba.ddsi.services.impl;

import ar.utn.ba.ddsi.models.dtos.HechoInputDTO;
import ar.utn.ba.ddsi.models.dtos.HechoOutputDTO;
import ar.utn.ba.ddsi.models.entities.hecho.Categoria;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.models.entities.importador.Importador;
import ar.utn.ba.ddsi.models.entities.importador.ImportadorCSV;
import ar.utn.ba.ddsi.models.repositories.IHechosRepository;
import ar.utn.ba.ddsi.services.IHechosServices;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import static ar.utn.ba.ddsi.conversores.HechoMapper.*;

@Service
public class HechosServices implements IHechosServices {

    @Autowired
    private IHechosRepository repositorioDeHechos;
    private Importador importadorCSV = new ImportadorCSV();
    private List<Categoria> categoriasExistentes = new ArrayList<>();

    @Override
    public void importarHechos(List<String> unosArchivos) throws IOException, CsvValidationException {
        for (String archivoIndice : unosArchivos) {
            for (HechoInputDTO hechoInputIndice : importadorCSV.importarHechos(archivoIndice)) {
                Hecho hechoConvertido = this.inputDTOAHecho(hechoInputIndice);
                hechoConvertido.setId(this.definirId());
                this.repositorioDeHechos.save(hechoConvertido);
            }
        }
    }

    public Hecho inputDTOAHecho(HechoInputDTO unHechoInput) {
        Hecho hecho = toHecho(unHechoInput);

        if (this.categoriasExistentes.contains(unHechoInput.getCategoria())) {
            hecho.getCategoria().setNombre(unHechoInput.getCategoria());
        } else {
            Categoria categoriaNueva = new Categoria(unHechoInput.getCategoria());
            hecho.getCategoria().setNombre(categoriaNueva.getNombre());
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

    public Long definirId() { // vuelva con el jpa !
        List<Hecho> solicitudesDeRepositorio = this.repositorioDeHechos.findAll();

        Long maxId = solicitudesDeRepositorio
                .stream()
                .map(Hecho::getId)
                .filter(Objects::nonNull)
                .max(Long::compareTo)
                .orElse(0L);

        return maxId + 1;
    }
}