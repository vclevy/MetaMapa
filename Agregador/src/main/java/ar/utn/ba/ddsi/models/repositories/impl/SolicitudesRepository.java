package ar.utn.ba.ddsi.models.repositories.impl;

import ar.utn.ba.ddsi.models.entities.hecho.solicitud.Solicitud;
import ar.utn.ba.ddsi.models.repositories.ISolicitudesRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SolicitudesRepository implements ISolicitudesRepository {
    private List<Solicitud> solicitudesDeEliminacion = new ArrayList<>();

    @Override
    public List<Solicitud> findAll() {
        return new ArrayList<>(solicitudesDeEliminacion);
    }

    @Override
    public Solicitud findById(int id) {
        return null;
    }

    @Override
    public void save(Solicitud hecho) {

    }

    @Override
    public void delete(Solicitud hecho) {

    }
}
