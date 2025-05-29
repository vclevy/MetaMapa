package ar.utn.ba.ddsi.FuenteProxy.services;

import ar.utn.ba.ddsi.FuenteProxy.models.entities.Coleccion;
import ar.utn.ba.ddsi.FuenteProxy.models.entities.Hecho;

import java.util.List;
import java.util.Map;

public interface IMetamapaServices {

    List<Hecho> obtenerHechos();

    List<Hecho> obtenerHechosConFiltros(Map<String, String> filtrosRaw);

    List<Coleccion> obtenerColecciones();

    List<Hecho> obtenerHechosDeColeccion(String identificador, Map<String, String> filtros);

    boolean enviarSolicitudEliminacion(Hecho unHecho, String justificacion);
}