package ar.utn.ba.ddsi.gateway.FuenteProxy.services;

import ar.utn.ba.ddsi.gateway.FuenteProxy.models.entities.Coleccion;
import ar.utn.ba.ddsi.gateway.FuenteProxy.models.entities.FiltroHecho.IFiltroHecho;
import ar.utn.ba.ddsi.gateway.FuenteProxy.models.entities.hecho.Hecho;
import java.util.List;

public interface IMetamapaServices {
    List<Hecho> obtenerHechos();
    List<Coleccion> obtenerColecciones();
    List<Hecho> obtenerHechosDeColeccionConFiltro(String identificador, List<IFiltroHecho> filtros);
    boolean enviarSolicitudEliminacion(Hecho unHecho, String justificacion);
    List<Hecho> navegarHechosDeColeccionConModo(String idColeccion, String modoNavegacion);
}
