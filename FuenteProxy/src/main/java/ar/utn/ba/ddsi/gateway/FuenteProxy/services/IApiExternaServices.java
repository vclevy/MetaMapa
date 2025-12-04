package ar.utn.ba.ddsi.gateway.FuenteProxy.services;

import ar.utn.ba.ddsi.gateway.FuenteProxy.models.dtos.output.HechoOutputDTO;
import ar.utn.ba.ddsi.gateway.FuenteProxy.models.entities.FiltroHecho.IFiltroHecho;
import java.util.List;

public interface IApiExternaServices {
    List<HechoOutputDTO> obtenerHechosDeAPI();
    List<HechoOutputDTO> obtenerHechosFiltrados(List<IFiltroHecho> filtros);
}


