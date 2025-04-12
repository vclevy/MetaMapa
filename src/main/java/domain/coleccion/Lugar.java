package domain.coleccion;

import lombok.Getter;

@Getter
public class Lugar {
    private String nombre;
    private Double latitud;
    private Double longitud;

    public Lugar(Double latitud, Double longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }
}
