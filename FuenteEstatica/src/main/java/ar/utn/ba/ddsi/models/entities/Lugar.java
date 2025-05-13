package ar.utn.ba.ddsi._tpa_ma_ma_grupo_4.FuenteEstatica.models.entities;

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
