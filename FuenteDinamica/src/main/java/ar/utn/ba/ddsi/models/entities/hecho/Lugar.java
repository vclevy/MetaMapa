package ar.utn.ba.ddsi.models.entities.hecho;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter @Setter
public class Lugar {
    private Double latitud;
    private Double longitud;
    private String provincia;
}
