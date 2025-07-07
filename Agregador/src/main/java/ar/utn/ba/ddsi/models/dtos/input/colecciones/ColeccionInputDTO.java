package ar.utn.ba.ddsi.models.dtos.input.colecciones;

import ar.utn.ba.ddsi.services.coleccionService.algoritmoConsenso.AlgoritmoDeConsenso;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ColeccionInputDTO {
    private String titulo;
    private String descripcion;
    private AlgoritmoDeConsenso algoritmo;
}
