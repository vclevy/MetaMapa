package ar.utn.ba.ddsi.FuenteProxy.services;

import ar.utn.ba.ddsi.FuenteProxy.models.dtos.OutPut.HechoOutputDTO;
import ar.utn.ba.ddsi.FuenteProxy.models.entities.FiltroHecho.IFiltroHecho;

import java.util.List;

public interface IApiExternaServices {
    List<HechoOutputDTO> obtenerHechosDeAPI();
    List<HechoOutputDTO> obtenerHechosFiltrados(List<IFiltroHecho> filtros);
}


