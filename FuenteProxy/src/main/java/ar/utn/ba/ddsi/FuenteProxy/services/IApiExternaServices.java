package ar.utn.ba.ddsi.FuenteProxy.services;

import ar.utn.ba.ddsi.FuenteProxy.models.entities.FiltroHecho.IFiltroHecho;
import ar.utn.ba.ddsi.FuenteProxy.models.entities.Hecho;
import java.util.List;

public interface IApiExternaServices {
    List<Hecho> obtenerHechosDeAPI();
    List<Hecho> obtenerHechosFiltrados(List<IFiltroHecho> filtros);
}


