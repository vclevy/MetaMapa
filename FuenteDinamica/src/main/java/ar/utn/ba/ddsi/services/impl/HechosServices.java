package ar.utn.ba.ddsi.services.impl;

import ar.utn.ba.ddsi.models.dtos.output.HechoOutputDTO;
import ar.utn.ba.ddsi.models.dtos.input.HechoInputDTO;
import ar.utn.ba.ddsi.models.entities.Usuario;
import ar.utn.ba.ddsi.models.entities.hecho.EstadoRevision;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.models.entities.hecho.Revision;
import ar.utn.ba.ddsi.models.entities.roles.Permisos;
import ar.utn.ba.ddsi.models.repositories.IHechosRepository;
import ar.utn.ba.ddsi.services.IHechosServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
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
        hechoOutputDTO.setEsEditable(hecho.esEditable());
        return hechoOutputDTO;
    }

    public HechoInputDTO hechoInputDTO(Hecho hecho) {
        HechoInputDTO dto = new HechoInputDTO();
        dto.setTitulo(hecho.getTitulo());
        dto.setDescripcion(hecho.getDescripcion());
        dto.setFechaDeAcontecimiento(hecho.getFechaDeAcontecimiento());
        dto.setLugar(hecho.getLugar());
        dto.setEtiquetas(hecho.getEtiquetas());
        dto.setMultimedia(hecho.getMultimedia());

        return dto;
    }

    public HechoOutputDTO findById(Integer id) {
        return hechoOutputDTO(repositorioDeHechos.findById(id));
    }

    public Hecho inputDTOAHecho(HechoInputDTO dto) {
        Hecho hecho = new Hecho();

        hecho.setTitulo(dto.getTitulo());
        hecho.setDescripcion(dto.getDescripcion());
        hecho.setFechaDeAcontecimiento(dto.getFechaDeAcontecimiento());
        hecho.setLugar(dto.getLugar());
        hecho.setEtiquetas(dto.getEtiquetas());
        hecho.setMultimedia(dto.getMultimedia());
        hecho.setFueEliminado(false);
        hecho.setEsAnonimo(hecho.esAnonimo());
        hecho.setSolicitudesDeEliminacion(new ArrayList<>());
        hecho.setContribuyente(null);

        return hecho;
    }

    @Override
    public void subirHecho(HechoInputDTO hecho, Usuario usuario) {
        Hecho nuevoHecho = inputDTOAHecho(hecho);
        nuevoHecho.setFechaDeCarga(LocalDateTime.now());
        nuevoHecho.setContribuyente(usuario);

        if (usuario.getRol().tenesPermiso(Permisos.SUBIR_HECHO)) {
            repositorioDeHechos.save(nuevoHecho);
        }
    }

    @Override
    public void editarHecho(int id, HechoInputDTO hechoModificado, Usuario usuario) {
        Hecho hechoOriginal = repositorioDeHechos.findById(id);
        if (hechoOriginal == null) {
            throw new RuntimeException("Hecho no encontrado");
        }

        if (!usuario.getRol().tenesPermiso(Permisos.EDITAR_HECHO)) {
            throw new RuntimeException("No tenés permiso para editar hechos");
        }

        if (!hechoOriginal.esEditable() || !usuario.equals(hechoOriginal.getContribuyente())) {
            throw new RuntimeException("Este hecho ya no puede ser editado o no sos su autor");
        }

        hechoOriginal.setTitulo(hechoModificado.getTitulo());
        hechoOriginal.setDescripcion((hechoModificado.getDescripcion()));
        hechoOriginal.setFechaDeAcontecimiento(hechoModificado.getFechaDeAcontecimiento());
        hechoOriginal.setLugar((hechoModificado.getLugar()));
        hechoOriginal.setEtiquetas(hechoModificado.getEtiquetas());
        hechoOriginal.setMultimedia(hechoModificado.getMultimedia());
        repositorioDeHechos.save(hechoOriginal);
    }
    public void revisarHecho(int id, EstadoRevision nuevoEstado, String comentario, Usuario admin) {
        if (!admin.getRol().tenesPermiso(Permisos.REVISAR_HECHO)) {
            throw new RuntimeException("No tenés permiso para revisar hechos");
        }

        Hecho hecho = repositorioDeHechos.findById(id);
        if (hecho == null) {
            throw new RuntimeException("Hecho no encontrado");
        }

        Revision nuevaRevision = new Revision(nuevoEstado, comentario);
        hecho.setRevision(nuevaRevision);
        repositorioDeHechos.save(hecho);
    }
}




