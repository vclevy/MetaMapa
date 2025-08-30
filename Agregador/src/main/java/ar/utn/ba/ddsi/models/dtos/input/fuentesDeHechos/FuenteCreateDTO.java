package ar.utn.ba.ddsi.models.dtos.input.fuentesDeHechos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FuenteCreateDTO {
    private String nombre;
    private String tipo;
    private String urlBase;
}
