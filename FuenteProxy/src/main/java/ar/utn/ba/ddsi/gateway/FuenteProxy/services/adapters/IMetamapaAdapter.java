package ar.utn.ba.ddsi.gateway.FuenteProxy.services.adapters;

import ar.utn.ba.ddsi.gateway.FuenteProxy.models.entities.Coleccion;
import ar.utn.ba.ddsi.gateway.FuenteProxy.models.entities.hecho.Hecho;
import java.util.List;

public interface IMetamapaAdapter {
    List<Hecho> obtenerHechos();
    List<Coleccion> obtenerColecciones();
    List<Hecho> obtenerHechosDeColeccion(String identificador);
    boolean enviarSolicitudEliminacion(Hecho unHecho, String justificacion);
    List<Hecho> obtenerHechosDeColeccionConModo(String idColeccion, String modoNavegacion);

}

