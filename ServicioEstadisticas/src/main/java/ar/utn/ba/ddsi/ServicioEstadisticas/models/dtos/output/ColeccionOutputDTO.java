package ar.utn.ba.ddsi.ServicioEstadisticas.models.dtos.output;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ColeccionOutputDTO {
    private String titulo;
    private List<HechoOutputDTO> hechos;
}