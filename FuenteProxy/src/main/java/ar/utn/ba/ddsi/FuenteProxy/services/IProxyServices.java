package ar.utn.ba.ddsi.FuenteProxy.services;

import ar.utn.ba.ddsi.FuenteProxy.models.entities.FiltroHecho.IFiltroHecho;
import ar.utn.ba.ddsi.FuenteProxy.models.entities.Hecho;

import java.util.List;
import java.util.Map;

public interface IProxyServices {
    List<Hecho> obtenerHechosDesdeAPI(); // sin filtros
    List<Hecho> obtenerHechosConFiltros(Map<String, String> filtros); // con filtros
    List<String> obtenerTodasLasColecciones();
    List<Hecho> obtenerHechosDeColeccion(String identificador, Map<String, String> filtros);
}

