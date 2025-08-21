package ar.utn.ba.ddsi.models.repositories.impl;

import ar.utn.ba.ddsi.models.entities.solicitud.Solicitud;
import ar.utn.ba.ddsi.models.repositories.ISolicitudesRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SolicitudesRepository implements ISolicitudesRepository {
    private List<Solicitud> solicitudesDeEliminacion;

    public SolicitudesRepository() {
        this.solicitudesDeEliminacion = new ArrayList<>();
    }

    @Override
    public List<Solicitud> findAll() {
        return this.solicitudesDeEliminacion;
    }

    @Override
    public Solicitud findById(Long id) {
        return this.solicitudesDeEliminacion.stream().filter(unaSolicitud -> unaSolicitud.getIdSolicitud().equals(id)).findFirst().orElse(null);
    }

    @Override
    public void save(Solicitud unaSolicitud) {
        solicitudesDeEliminacion.add(unaSolicitud);
    }

    @Override
    public void delete(Solicitud unaSolicitud) {
        this.solicitudesDeEliminacion.remove(unaSolicitud);
    }
}
