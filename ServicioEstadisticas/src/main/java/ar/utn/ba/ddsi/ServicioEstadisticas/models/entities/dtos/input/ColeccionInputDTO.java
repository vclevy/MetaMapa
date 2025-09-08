package ar.utn.ba.ddsi.ServicioEstadisticas.models.entities.dtos.input;

import lombok.Data;

import java.util.List;
@Data
public class ColeccionInputDTO {
    private String titulo;
    private List<HechoInputDTO> hechosDeLaColeccion;
}
