package ar.utn.ba.ddsi.gateway.models.entities.coleccion;

import ar.utn.ba.ddsi.gateway.models.entities.hecho.Categoria;
import ar.utn.ba.ddsi.gateway.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.gateway.models.entities.hecho.Lugar;

import java.time.LocalDateTime;

public class VerificadorDeCriterios {

    public static boolean cumpleCategoria(Hecho hecho, Categoria categoria) {
        return hecho.getCategoria() == categoria;
    }

    public static boolean cumpleLugar(Hecho hecho, Lugar lugar) {
        return hecho.getLugar() == lugar;
    }

    public static boolean entreFechas(Hecho hecho, LocalDateTime desde, LocalDateTime hasta) {
        LocalDateTime fecha = hecho.getFechaDeAcontecimiento();
        return (fecha.isEqual(desde) || fecha.isAfter(desde)) &&
                (fecha.isEqual(hasta) || fecha.isBefore(hasta));
    }
}