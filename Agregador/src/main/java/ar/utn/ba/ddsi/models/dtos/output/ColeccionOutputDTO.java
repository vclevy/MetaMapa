package ar.utn.ba.ddsi.models.dtos.output;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class ColeccionOutputDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private List<HechoOutputDTO> hechosDeLaColeccion;
    private String handle;
    private String algoritmoDeConsenso;
    private List<FuenteDeHechoOutputDTO> fuentesDeHechos;
}
