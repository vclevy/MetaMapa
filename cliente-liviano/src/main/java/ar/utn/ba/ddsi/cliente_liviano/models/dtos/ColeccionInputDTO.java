package ar.utn.ba.ddsi.cliente_liviano.models.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ColeccionInputDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private List<HechoInputDTO> hechosDeLaColeccion;
    private String algoritmoDeConsenso;
    //private List<FuenteDeHechoOutputDTO> fuentesDeHechos;
}
