package ar.utn.ba.ddsi.models.entities.hecho.filtroHecho;

import ar.utn.ba.ddsi.models.entities.hecho.Hecho;

public interface FiltroHecho {
    boolean aplica(Hecho hecho);
}
