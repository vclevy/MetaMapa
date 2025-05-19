package ar.utn.ba.ddsi.services.impl;

import ar.utn.ba.ddsi.models.entities.hecho.solicitud.Solicitud;
import ar.utn.ba.ddsi.models.repositories.IHechosRepository;
import ar.utn.ba.ddsi.models.repositories.ISolicitudesRepository;
import ar.utn.ba.ddsi.services.ISolcitudesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SolicitudesService implements ISolcitudesService {
    @Autowired
    private ISolicitudesRepository solicitudesRepository;

    @Autowired
    private IHechosRepository hechosRepository;

    @Override
    public void registrarSolicitud(Solicitud unaSolicitud) {
        // GUARDO SOLICITUD EN EL REPO DE SOLICITUDES
        solicitudesRepository.save(unaSolicitud);

        // GUARDO EN EL HISTORIAL DE SOLICITUDES DEL HECHO EN EL REPO DE HECHOS
        if (hechosRepository.findById(unaSolicitud.getId()) == unaSolicitud.getHecho()) {
            unaSolicitud.getHecho().getSolicitudesDeEliminacion().add(unaSolicitud);
            hechosRepository.save(unaSolicitud.getHecho());
        }
    }
}
