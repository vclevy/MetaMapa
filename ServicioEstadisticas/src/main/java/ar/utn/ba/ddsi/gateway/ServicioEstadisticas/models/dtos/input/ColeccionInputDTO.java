package ar.utn.ba.ddsi.gateway.ServicioEstadisticas.models.dtos.input;

import lombok.Data;

import java.util.List;
@Data
public class ColeccionInputDTO {
    private String titulo;
    private List<HechoInputDTO> hechosDeLaColeccion;
}
