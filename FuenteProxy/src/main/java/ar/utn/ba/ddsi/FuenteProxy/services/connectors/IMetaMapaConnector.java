package ar.utn.ba.ddsi.FuenteProxy.services.connectors;

import ar.utn.ba.ddsi.FuenteProxy.models.dtos.ColeccionDTO;
import ar.utn.ba.ddsi.FuenteProxy.models.dtos.HechoProxyDTO;
import ar.utn.ba.ddsi.FuenteProxy.models.dtos.SolicitudEliminacionInputDTO;
import java.util.List;

public interface IMetaMapaConnector {
    List<HechoProxyDTO> obtenerHechos();
    List<HechoProxyDTO> obtenerHechosDeColeccion(String identificador);
    List<HechoProxyDTO> obtenerHechosDeColeccionConModo(String identificador, String modoNavegacion);
    List<ColeccionDTO> obtenerColecciones();
    boolean enviarSolicitudEliminacion(SolicitudEliminacionInputDTO dto);
}