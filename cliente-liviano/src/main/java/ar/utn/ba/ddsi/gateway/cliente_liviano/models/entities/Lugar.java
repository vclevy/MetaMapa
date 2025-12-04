package ar.utn.ba.ddsi.gateway.cliente_liviano.models.entities;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Lugar {
    private Double latitud;
    private Double longitud;
    private String provincia;

    public Lugar(Double latitud, Double longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public Lugar() {

    }
}