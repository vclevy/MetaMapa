package ar.utn.ba.ddsi.gateway.models.dtos.input.colecciones;

import ar.utn.ba.ddsi.gateway.services.coleccionService.algoritmoConsenso.AlgoritmoDeConsenso;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ColeccionPatchDTO {
    private String campo;
    private String nuevoValor;
    private AlgoritmoDeConsenso nuevoAlgoritmoDeConsenso;
}
