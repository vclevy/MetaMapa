package ar.utn.ba.ddsi.FuenteProxy.models.entities;

import ar.utn.ba.ddsi.FuenteProxy.models.entities.hecho.Hecho;

public interface Criterio {
    public boolean cumple(Hecho unHecho);
}
