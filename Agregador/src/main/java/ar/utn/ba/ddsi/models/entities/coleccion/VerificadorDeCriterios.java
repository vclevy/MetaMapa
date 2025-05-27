package ar.utn.ba.ddsi.models.entities.coleccion;

import ar.utn.ba.ddsi.models.entities.hecho.Categoria;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.models.entities.hecho.Lugar;

import java.time.LocalDate;

public class VerificadorDeCriterios {

    public static boolean cumpleCategoria(Hecho hecho, Categoria categoria) {
        return hecho.getCategoria() == categoria;
    }

    public static boolean cumpleLugar(Hecho hecho, Lugar lugar) {
        return hecho.getLugar() == lugar;
    }

    public static boolean entreFechas(Hecho hecho, LocalDate desde, LocalDate hasta) {
        LocalDate fecha = hecho.getFechaDeAcontecimiento();
        return (fecha.isEqual(desde) || fecha.isAfter(desde)) &&
                (fecha.isEqual(hasta) || fecha.isBefore(hasta));
    }
}