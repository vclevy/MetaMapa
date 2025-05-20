package ar.utn.ba.ddsi.services.impl;

import ar.utn.ba.ddsi.models.dtos.output.HechoOutputDTO;
import ar.utn.ba.ddsi.models.entities.Hecho;
import ar.utn.ba.ddsi.models.fuentes.FuenteEstatica;
import ar.utn.ba.ddsi.models.repositories.IHechosRepository;
import ar.utn.ba.ddsi.services.IHechosServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HechosServices implements IHechosServices {
    @Autowired
    private IHechosRepository repositorioDeHechos;

    public List<Hecho> importarCSV() {
        FuenteEstatica fuenteEstatica = new FuenteEstatica();
        return fuenteEstatica.importarHechos();
    }

    private HechoOutputDTO hechoOutputDTO(Hecho hecho) {
        HechoOutputDTO hechoOutputDTO = new HechoOutputDTO();
        hechoOutputDTO.setId(hecho.getId());
        hechoOutputDTO.setTitulo((hecho.getTitulo()));
        hechoOutputDTO.setDescripcion(hecho.getDescripcion());
        hechoOutputDTO.setCategoria(hecho.getCategoria());
        hechoOutputDTO.setFechaDeAcontecimiento(hecho.getFechaDeAcontecimiento());
        hechoOutputDTO.setLugar(hecho.getLugar());
        hechoOutputDTO.setOrigen(hecho.getOrigen());
        hechoOutputDTO.setSolicitudesDeEliminacion(hecho.getSolicitudesDeEliminacion());
        hechoOutputDTO.setEtiquetas(hecho.getEtiquetas());
        hechoOutputDTO.setFueEliminado(hecho.getFueEliminado());
        return hechoOutputDTO;
    }
}
