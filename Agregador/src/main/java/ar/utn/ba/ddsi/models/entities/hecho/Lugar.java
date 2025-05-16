package ar.utn.ba.ddsi.models.entities.hecho;

import lombok.Getter;

@Getter
public class Lugar {
    private Double latitud;
    private Double longitud;

    public Lugar(Double latitud, Double longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }
}