package ar.utn.ba.ddsi.FuenteProxy.services;

import ar.utn.ba.ddsi.FuenteProxy.models.entities.FiltroHecho.IFiltroHecho;
import ar.utn.ba.ddsi.FuenteProxy.models.entities.Hecho;
import java.util.List;

public interface IApiCatedraServices {
    List<Hecho> obtenerHechosDesdeAPI();
    List<Hecho> obtenerHechosConFiltros(List<IFiltroHecho> filtros);
}


