package ar.utn.ba.ddsi._tpa_ma_ma_grupo_4.FuenteDinamica.models.repositories.impl;

import ar.utn.ba.ddsi._tpa_ma_ma_grupo_4.FuenteDinamica.models.repositories.ISolicitudesRepository;
import ar.utn.ba.ddsi._tpa_ma_ma_grupo_4.FuenteEstatica.models.hecho.solicitudes.Solicitud;

import java.util.ArrayList;
import java.util.List;

public class SolicitudesRepository implements ISolicitudesRepository {
    private List<Solicitud> solicitudesDeEliminacion = new ArrayList<>();

    @Override
    public List<Solicitud> findAll() {
        return new ArrayList<>(solicitudesDeEliminacion);
    }

    @Override
    public Solicitud findById(int id) {
        return solicitudesDeEliminacion.stream().filter(unaSolicitud -> unaSolicitud.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void save(Solicitud solicitud) {
        solicitudesDeEliminacion.add(solicitud);
    }

    @Override
    public void delete(Solicitud solicitud) {
        solicitudesDeEliminacion.remove(solicitud);
    }
}

