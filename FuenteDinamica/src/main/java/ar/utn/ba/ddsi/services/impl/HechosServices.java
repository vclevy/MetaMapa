package ar.utn.ba.ddsi.services.impl;

import ar.utn.ba.ddsi.models.dtos.input.HechoInputDTO;
import ar.utn.ba.ddsi.models.dtos.output.HechoOutputDTO;
import ar.utn.ba.ddsi.models.entities.Usuario;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.models.entities.roles.Permisos;
import ar.utn.ba.ddsi.models.repositories.IHechosRepository;
import ar.utn.ba.ddsi.services.IHechosServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;


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

    public HechoInputDTO hechoInputDTO(Hecho hecho) {
        HechoInputDTO dto = new HechoInputDTO();
        dto.setTitulo(hecho.getTitulo());
        dto.setDescripcion(hecho.getDescripcion());
        dto.setCategoriaId(hecho.getCategoria());
        dto.setFechaDeAcontecimiento(hecho.getFechaDeAcontecimiento());
        dto.setLugar(hecho.getLugar());
        dto.setOrigen(hecho.getOrigen());
        dto.setEtiquetas(hecho.getEtiquetas());
        dto.setMultimedia(hecho.getMultimedia());

        return dto;
    }

    public Hecho inputDTOAHecho(HechoInputDTO dto) {
        Hecho hecho = new Hecho();

        hecho.setTitulo(dto.getTitulo());
        hecho.setDescripcion(dto.getDescripcion());
        hecho.setCategoria(dto.getCategoriaId());
        hecho.setFechaDeAcontecimiento(dto.getFechaDeAcontecimiento());
        hecho.setLugar(dto.getLugar());
        hecho.setOrigen(dto.getOrigen());
        hecho.setEtiquetas(dto.getEtiquetas());
        hecho.setMultimedia(dto.getMultimedia());

        // Valores por defecto o a definir fuera del DTO
        hecho.setFechaDeCarga(LocalDateTime.now());
        hecho.setFueEliminado(false);
        hecho.setEsAnonimo(true);
        hecho.setSolicitudesDeEliminacion(new ArrayList<>());
        hecho.setContribuyente(null);

        return hecho;
    }

    @Override
    public void subirHecho(HechoInputDTO hecho, Usuario usuario) {
        Hecho nuevoHecho = inputDTOAHecho(hecho);
        nuevoHecho.setFechaDeCarga(LocalDateTime.now());
        nuevoHecho.setContribuyente(usuario);

        if(usuario.getRol().tenesPermiso(Permisos.SUBIR_HECHO)){
            repositorioDeHechos.save(nuevoHecho);
        }
    }


    public void editarHecho(HechoInputDTO hecho, Usuario usuario) {
        if (usuario.getRol().tenesPermiso(Permisos.EDITAR_HECHO)) {
            if (ChronoUnit.DAYS.between(hecho.getFechaDeCarga(), LocalDateTime.now()) < 7) {
                //TODO logica de editar un hecho
            }
            throw new RuntimeException("Ya no puedes editar este hecho");

        }
        throw new SecurityException("No tienes permiso para realizar esta acción");
    }

}




