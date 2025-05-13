package ar.utn.ba.ddsi._tpa_ma_ma_grupo_4.FuenteDinamica.services.impl;

import ar.utn.ba.ddsi._tpa_ma_ma_grupo_4.FuenteDinamica.models.dtos.output.HechoOutputDTO;
import ar.utn.ba.ddsi._tpa_ma_ma_grupo_4.FuenteDinamica.models.entities.Hecho;
import ar.utn.ba.ddsi._tpa_ma_ma_grupo_4.FuenteDinamica.models.repositories.IHechosRepository;
import ar.utn.ba.ddsi._tpa_ma_ma_grupo_4.FuenteDinamica.services.IHechosServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class HechosServices implements IHechosServices {
    @Autowired
    private IHechosRepository repositorioDeHechos;

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
