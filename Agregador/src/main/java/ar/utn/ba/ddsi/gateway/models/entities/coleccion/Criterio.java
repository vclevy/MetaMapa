package ar.utn.ba.ddsi.gateway.models.entities.coleccion;

import ar.utn.ba.ddsi.gateway.models.entities.hecho.Hecho;

public interface Criterio {
    boolean cumple(Hecho hecho);
}
