package ar.utn.ba.ddsi.FuenteProxy.models.entities.FiltroHecho;

import ar.utn.ba.ddsi.FuenteProxy.models.entities.hecho.Hecho;

public interface IFiltroHecho{
    boolean aplica(Hecho hecho);
}
