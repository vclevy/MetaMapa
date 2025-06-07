package ar.utn.ba.ddsi.services.impl;

import ar.utn.ba.ddsi.models.dtos.HechoInputDTO;
import ar.utn.ba.ddsi.models.dtos.HechoOutputDTO;
import ar.utn.ba.ddsi.models.entities.hecho.Categoria;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.models.entities.hecho.Lugar;
import ar.utn.ba.ddsi.models.entities.importador.Importador;
import ar.utn.ba.ddsi.models.entities.importador.ImportadorCSV;
import ar.utn.ba.ddsi.models.entities.importador.LectorCSV;
import ar.utn.ba.ddsi.models.repositories.IHechosRepository;
import ar.utn.ba.ddsi.services.IHechosServices;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
                Hecho hechoConvertido = this.procesarHechosInput(hechoInputIndice);
                hechoConvertido.setId(this.definirId());
                this.repositorioDeHechos.save(hechoConvertido);
            }
        }
    }

    @Override
    public Hecho procesarHechosInput(HechoInputDTO unHechoInput) {
        Hecho hecho = convertirHechoInputEnHecho(unHechoInput);

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

    @Override
    public HechoOutputDTO hechoOutputDTO(Hecho hecho) {
        HechoOutputDTO hechoOutputDTO = new HechoOutputDTO(
                hecho.getId(),
                hecho.getTitulo(),
                hecho.getDescripcion(),
                hecho.getCategoria(),
                hecho.getFechaDeAcontecimiento(),
                hecho.getFechaDeCarga(),
                hecho.getLugar(),
                hecho.getMultimedia()
        );

        return hechoOutputDTO;
    }

    @Override
    public Hecho convertirHechoInputEnHecho(HechoInputDTO hechoInputDTO) {
        Hecho unHecho = new Hecho (
                hechoInputDTO.getTitulo(),
                hechoInputDTO.getDescripcion(),
                hechoInputDTO.getFechaDelHecho(),
                hechoInputDTO.getLugar()
        );
        return unHecho;
    }

    @Override
    public Long definirId() {
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
