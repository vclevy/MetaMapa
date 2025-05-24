package ar.utn.ba.ddsi.FuenteProxy.services;

import ar.utn.ba.ddsi.FuenteProxy.models.entities.Hecho;

import java.util.List;
import java.util.Map;

public interface IHechosService {
    List<Hecho> obtenerHechos();
    List<Hecho> obtenerHechosConFiltros(Map<String, String> filtrosRaw);
}
