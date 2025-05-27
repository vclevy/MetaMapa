package ar.utn.ba.ddsi.FuenteProxy.services;

import ar.utn.ba.ddsi.FuenteProxy.models.entities.Hecho;
import java.util.List;
import java.util.Map;

public interface IApiCatedraServices {
    List<Hecho> obtenerHechosDesdeAPI();
    List<Hecho> obtenerHechosConFiltros(Map<String, String> filtros);
}

