package ar.utn.ba.ddsi.FuenteProxy.models.entities.hecho;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Lugar {
    private Double latitud;
    private Double longitud;
}
