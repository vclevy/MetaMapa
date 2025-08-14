package ar.utn.ba.ddsi.models.dtos.output;

import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.services.fuentes.Fuente;
import ar.utn.ba.ddsi.services.fuentes.IFuenteDeHechos;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ColeccionOutputDTO {
    private String titulo;
    private String descripcion;
    private List<HechoOutputDTO> hechosDeLaColeccion;
    private String handle;
    private String algoritmoDeConsenso;
    private List<FuenteDeHechoOutputDTO> fuentesDeHechos;
}
