package ar.utn.ba.ddsi.services.impl;

import ar.utn.ba.ddsi.models.dtos.input.HechoInputDTO;
import ar.utn.ba.ddsi.models.dtos.output.HechoOutputDTO;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.models.repositories.IHechosRepository;
import ar.utn.ba.ddsi.services.IHechosService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class HechosService implements IHechosService {
    //@Autowired
    private IHechosRepository hechosRepository;

    @Override
    public HechoOutputDTO findById(Integer id) {
        var hecho = this.hechosRepository.findById(id);
        if(hecho == null) {
            return null;
        }
        return hechoOutputDTO(hecho);
    }

    @Override
    public void eliminar(Integer id) {
        var hecho = this.hechosRepository.findById(id);
        if(hecho != null){
            this.hechosRepository.delete(hecho);
        }
    }

    @Override
    public List<HechoOutputDTO> findAll() {
        return this.hechosRepository.findAll().stream().map(this::hechoOutputDTO).collect(Collectors.toList());
    }

    // TODO: FALTARIA PODER RECOLECTAR LOS HECHOS DE LAS TRES FUENTES Y GUARDARLOS EN EL REPO DE HECHOS



    private HechoOutputDTO hechoOutputDTO(Hecho unHecho) {
        HechoOutputDTO hechoOutputDTO = new HechoOutputDTO();
        hechoOutputDTO.setTitulo(unHecho.getTitulo());
        hechoOutputDTO.setDescripcion(unHecho.getDescripcion());
        hechoOutputDTO.setCategoria(unHecho.getCategoria());
        hechoOutputDTO.setFechaDeAcontecimiento(unHecho.getFechaDeAcontecimiento());
        hechoOutputDTO.setLugar(unHecho.getLugar());
        hechoOutputDTO.setSolicitudesDeEliminacion(unHecho.getSolicitudesDeEliminacion());
        hechoOutputDTO.setEtiquetas(unHecho.getEtiquetas());
        hechoOutputDTO.setMultimedia(unHecho.getMultimedia());
        hechoOutputDTO.setContribuyente(unHecho.getContribuyente());
        return hechoOutputDTO;
    }
}
