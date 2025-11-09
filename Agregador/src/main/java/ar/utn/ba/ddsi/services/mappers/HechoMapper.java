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
        dto.setNombreDeUsuario(hecho.getNombreDeUsuario());
        dto.setNombreArchivo(hecho.getNombreArchivo());
        dto.setEsAnonimo(hecho.getEsAnonimo());
        dto.setIdEnFuente(hecho.getIdEnFuente());
        dto.setFuenteNombre(hecho.getFuente().getTipo());

        return dto;
    }
}
