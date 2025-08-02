package ar.utn.ba.ddsi.FuenteProxy.models.repositories;

import ar.utn.ba.ddsi.FuenteProxy.models.entities.solicitudEliminacion.SolicitudEliminacion;

import java.util.List;
import java.util.UUID;

public interface ISolicitudesRepository {
    List<SolicitudEliminacion> findAll();

    SolicitudEliminacion findById(UUID id);

    void save(SolicitudEliminacion solicitud);

    void delete(SolicitudEliminacion solicitud);
}
