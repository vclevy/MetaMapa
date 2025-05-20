package ar.utn.ba.ddsi.models.dtos.input;

import ar.utn.ba.ddsi.models.dtos.output.HechoOutputDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ColeccionInputDTO {
    private String titulo;
    private String descripcion;
    private List<HechoInputDTO> hechosInputDtos;
}
