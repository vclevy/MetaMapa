package ar.utn.ba.ddsi.gateway.FuenteProxy.services.connectors;

import ar.utn.ba.ddsi.gateway.FuenteProxy.models.dtos.ColeccionDTO;
import ar.utn.ba.ddsi.gateway.FuenteProxy.models.dtos.input.HechoInputDTO;
import ar.utn.ba.ddsi.gateway.FuenteProxy.models.dtos.SolicitudEliminacionInputDTO;
import java.util.List;

public interface IMetaMapaConnector {
    List<HechoInputDTO> obtenerHechos();
    List<HechoInputDTO> obtenerHechosDeColeccion(String identificador);
    List<HechoInputDTO> obtenerHechosDeColeccionConModo(String identificador, String modoNavegacion);
    List<ColeccionDTO> obtenerColecciones();
    boolean enviarSolicitudEliminacion(SolicitudEliminacionInputDTO dto);
}