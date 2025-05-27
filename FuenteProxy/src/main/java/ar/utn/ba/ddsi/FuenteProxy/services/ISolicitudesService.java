package ar.utn.ba.ddsi.FuenteProxy.services;

import ar.utn.ba.ddsi.FuenteProxy.models.entities.Hecho;

public interface ISolicitudesService {
    boolean enviarSolicitudEliminacion(Hecho unHecho, String justificacion);
}
