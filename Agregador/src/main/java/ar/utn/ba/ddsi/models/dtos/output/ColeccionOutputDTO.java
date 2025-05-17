package ar.utn.ba.ddsi.models.dtos.output;

import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ColeccionOutputDTO {
    private String titulo;
    private String descripcion;
    private String handle;
    private List<HechoOutputDTO> hechosOutputDtos;
}
