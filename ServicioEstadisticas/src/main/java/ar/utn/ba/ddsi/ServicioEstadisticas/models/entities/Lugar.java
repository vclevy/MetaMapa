package ar.utn.ba.ddsi.ServicioEstadisticas.models.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Lugar {
    private Double latitud;
    private Double longitud;

    public Lugar(Double latitud, Double longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }
}