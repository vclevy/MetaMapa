package ar.utn.ba.ddsi._tpa_ma_ma_grupo_4.FuenteEstatica.models.hecho.filtroHecho;

import ar.utn.ba.ddsi._tpa_ma_ma_grupo_4.FuenteEstatica.models.entities.Hecho;

public interface FiltroHecho {
    boolean aplica(Hecho hecho);
}
