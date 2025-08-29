package ar.utn.ba.ddsi.FuenteProxy.models.dtos;

import ar.utn.ba.ddsi.FuenteProxy.models.dtos.input.HechoInputDTO;
import lombok.Data;

import java.util.List;

@Data
public class ColeccionDTO {
    private String titulo;
    private String descripcion;
    private List<HechoInputDTO> hechosOutputDtos;
}
