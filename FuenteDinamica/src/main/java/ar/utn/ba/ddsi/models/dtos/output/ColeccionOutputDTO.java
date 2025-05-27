package ar.utn.ba.ddsi.models.dtos.output;

import ar.utn.ba.ddsi._tpa_ma_ma_grupo_4.FuenteEstatica.models.dtos.output.HechoOutputDTO;

import java.util.List;

public class ColeccionOutputDTO {
    private Integer id;
    private String titulo;
    private String descripcion;
    private List<HechoOutputDTO> hechos;
}
