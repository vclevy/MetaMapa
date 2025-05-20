package ar.utn.ba.ddsi.FuenteProxy.services;

import ar.utn.ba.ddsi.FuenteProxy.models.entities.Hecho;

import java.util.List;
import java.util.Map;

public interface IMetamapaServices {

    List<Hecho> obtenerHechos(Map<String, String> filtros);

    List<String> obtenerColecciones();

    List<Hecho> obtenerHechosDeColeccion(String identificador, Map<String, String> filtros);

    boolean enviarSolicitudEliminacion(SolicitudEliminacionDTO solicitud);
}
