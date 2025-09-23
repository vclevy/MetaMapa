package ar.utn.ba.ddsi.cliente_liviano.models.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ColeccionDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private List<HechoDTO> hechosDeLaColeccion;
    private String algoritmoDeConsenso;
    //private List<FuenteDeHechoOutputDTO> fuentesDeHechos;
}
