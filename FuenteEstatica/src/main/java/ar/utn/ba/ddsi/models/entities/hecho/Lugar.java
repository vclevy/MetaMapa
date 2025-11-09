package ar.utn.ba.ddsi.models.entities.hecho;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Lugar {
    private Double latitud;
    private Double longitud;

    public Lugar(Double latitud, Double longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public Lugar() {

    }
}

