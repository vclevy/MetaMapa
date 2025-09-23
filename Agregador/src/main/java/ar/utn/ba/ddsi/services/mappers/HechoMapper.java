package ar.utn.ba.ddsi.services.mappers;

import ar.utn.ba.ddsi.models.dtos.output.HechoOutputDTO;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import org.springframework.stereotype.Component;

@Component
public class HechoMapper {
    public HechoOutputDTO toDTO(Hecho hecho) {
        HechoOutputDTO dto = new HechoOutputDTO();
        dto.setId(hecho.getId());
        dto.setTitulo(hecho.getTitulo());
        dto.setDescripcion(hecho.getDescripcion());
        dto.setCategoriaNombre(hecho.getCategoria().getNombre());
        dto.setFechaDeAcontecimiento(hecho.getFechaDeAcontecimiento());
        dto.setLugar(hecho.getLugar());
        dto.setEtiquetas(hecho.getEtiquetas());
        dto.setMultimedia(hecho.getMultimedia());
        dto.setSolicitudesDeEliminacion(hecho.getSolicitudesDeEliminacion());
        dto.setContribuyente(hecho.getUsuarioContribuyente());

        if (hecho.getFuente() != null) {
            dto.setFuenteNombre(hecho.getFuente().getTipo());
        }
        return dto;
    }
}
