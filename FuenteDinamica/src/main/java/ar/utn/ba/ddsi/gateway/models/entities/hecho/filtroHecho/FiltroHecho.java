package ar.utn.ba.ddsi.gateway.models.entities.hecho.filtroHecho;

import ar.utn.ba.ddsi.gateway.models.entities.hecho.Hecho;

public interface FiltroHecho {
    boolean aplica(Hecho hecho);
}
