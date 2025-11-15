package ar.utn.ba.ddsi.gateway.models.dtos.output;

import lombok.Data;

import java.util.List;

@Data
public class ColeccionOutputDTO {
    private Integer id;
    private String titulo;
    private String descripcion;
    private List<HechoOutputDTO> hechos;
}
