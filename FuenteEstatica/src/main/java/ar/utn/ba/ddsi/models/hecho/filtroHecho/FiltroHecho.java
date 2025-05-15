package ar.utn.ba.ddsi.models.hecho.filtroHecho;

import ar.utn.ba.ddsi.models.entities.Hecho;

public interface FiltroHecho {
    boolean aplica(Hecho hecho);
}
