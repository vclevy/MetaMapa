package ar.utn.ba.ddsi.models.coleccion;

import ar.utn.ba.ddsi._tpa_ma_ma_grupo_4.FuenteEstatica.models.entities.Hecho;

public interface CriterioDePertenencia {
    public boolean cumple(Hecho unHecho);
}


