package ar.utn.ba.ddsi.FuenteProxy.models.repositories.impl;

import ar.utn.ba.ddsi.FuenteProxy.models.entities.Hecho;
import ar.utn.ba.ddsi.FuenteProxy.models.entities.SolicitudEliminacion;
import ar.utn.ba.ddsi.FuenteProxy.models.repositories.ISolicitudesRepository;
import jdk.jfr.Percentage;

import java.util.List;
import java.util.Map;
import java.util.*;

public class SolicitudesRepository implements ISolicitudesRepository {

    private final Map<UUID, SolicitudEliminacion> solicitudes = new HashMap<>();

    @Override
    public List<SolicitudEliminacion> findAll() {
        return new ArrayList<>(solicitudes.values());
    }

    @Override
    public SolicitudEliminacion findById(UUID id) {
        return solicitudes.get(id);
    }

    @Override
    public void save(SolicitudEliminacion solicitud) {
        solicitudes.put(solicitud.getId(), solicitud);
    }

    @Override
    public void delete(SolicitudEliminacion solicitud) {
        solicitudes.remove(solicitud.getId());
    }
}
