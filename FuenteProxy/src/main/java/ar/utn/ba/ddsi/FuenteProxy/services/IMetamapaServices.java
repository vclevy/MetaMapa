package ar.utn.ba.ddsi.FuenteProxy.services;

import ar.utn.ba.ddsi.FuenteProxy.models.entities.Coleccion;
import ar.utn.ba.ddsi.FuenteProxy.models.entities.FiltroHecho.IFiltroHecho;
import ar.utn.ba.ddsi.FuenteProxy.models.entities.Hecho;

import java.util.List;

public interface IMetamapaServices {
    List<Hecho> obtenerHechos();
    List<Coleccion> obtenerColecciones();
    List<Hecho> obtenerHechosDeColeccionConFiltro(String identificador, List<IFiltroHecho> filtros);
    boolean enviarSolicitudEliminacion(Hecho unHecho, String justificacion);
    List<Hecho> navegarHechosDeColeccionConModo(String idColeccion, String modoNavegacion);
}
