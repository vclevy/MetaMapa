package ar.utn.ba.ddsi.gateway.FuenteProxy.models.entities;

import ar.utn.ba.ddsi.gateway.FuenteProxy.models.entities.hecho.Hecho;

public interface Criterio {
    boolean cumple(Hecho unHecho);
}
