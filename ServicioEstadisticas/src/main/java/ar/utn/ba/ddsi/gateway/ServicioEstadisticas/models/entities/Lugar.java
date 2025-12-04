package ar.utn.ba.ddsi.gateway.ServicioEstadisticas.models.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Lugar {
    private String provincia;
    private Double latitud;
    private Double longitud;

    public Lugar(String provincia, Double latitud, Double longitud) {
        this.provincia = provincia;
        this.latitud = latitud;
        this.longitud = longitud;
    }
}