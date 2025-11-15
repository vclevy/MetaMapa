package ar.utn.ba.ddsi.gateway.FuenteProxy.models.entities.FiltroHecho;

import ar.utn.ba.ddsi.gateway.FuenteProxy.models.entities.hecho.Hecho;

public interface IFiltroHecho{
    boolean aplica(Hecho hecho);
}
