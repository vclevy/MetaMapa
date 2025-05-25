package ar.utn.ba.ddsi.models.entities.coleccion;

import ar.utn.ba.ddsi.models.entities.hecho.Hecho;

public interface Criterio {
    boolean cumple(Hecho hecho);
}
