package ar.utn.ba.ddsi.gateway.cliente_liviano.models;


import ar.utn.ba.ddsi.gateway.cliente_liviano.models.dtos.HechoFormDTO;
import ar.utn.ba.ddsi.gateway.cliente_liviano.models.dtos.HechoOutputFrontDTO;
import ar.utn.ba.ddsi.gateway.cliente_liviano.models.entities.Lugar;

public class HechoMapper {

    public static HechoFormDTO toFormDTO(HechoOutputFrontDTO hechoOutput) {
        if (hechoOutput == null) return null;

        HechoFormDTO formDTO = new HechoFormDTO();
        formDTO.setTitulo(hechoOutput.getTitulo());
        formDTO.setDescripcion(hechoOutput.getDescripcion());
        formDTO.setCategoriaNombre(hechoOutput.getCategoriaNombre());
        formDTO.setFechaDeAcontecimiento(hechoOutput.getFechaDeAcontecimiento());

        // Asumiendo que lugar nunca es null, sino habría que validar
        Lugar lugar = hechoOutput.getLugar();
        if (lugar != null) {
            formDTO.setLatitud(lugar.getLatitud());
            formDTO.setLongitud(lugar.getLongitud());
        }

        formDTO.setNombreDeUsuario(hechoOutput.getNombreDeUsuario());
        formDTO.setEsAnonimo(false);

        return formDTO;
    }
}