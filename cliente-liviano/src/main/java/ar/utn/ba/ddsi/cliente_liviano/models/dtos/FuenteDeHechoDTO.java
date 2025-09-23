package ar.utn.ba.ddsi.cliente_liviano.models.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FuenteDeHechoDTO {
    private String handle;
    private String tipo;
    private String urlBase;
}
