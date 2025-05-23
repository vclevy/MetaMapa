package ar.utn.ba.ddsi.services.solicitudService;

import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.models.entities.hecho.solicitud.EstadoDeSolicitudDeEliminacion;
import ar.utn.ba.ddsi.models.entities.hecho.solicitud.HistorialSolicitud;
import ar.utn.ba.ddsi.models.entities.hecho.solicitud.Solicitud;
import ar.utn.ba.ddsi.models.entities.roles.Usuario;
import ar.utn.ba.ddsi.models.repositories.IHechosRepository;
import ar.utn.ba.ddsi.models.repositories.ISolicitudesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SolicitudesService implements ISolcitudesService {
    @Autowired
    private ISolicitudesRepository solicitudesRepository;

    @Autowired
    private IHechosRepository hechosRepository;

    @Override
    public void registrarSolicitud(Solicitud unaSolicitud, Usuario usuarioModificador) {
        // GUARDO SOLICITUD EN EL REPO DE SOLICITUDES
        this.actualizarHistorialDe(unaSolicitud, usuarioModificador);
        solicitudesRepository.save(unaSolicitud);

        // GUARDO EN EL HISTORIAL DE SOLICITUDES DEL HECHO EN EL REPO DE HECHOS
        Hecho hechoPersistido = hechosRepository.findById(unaSolicitud.getHecho().getId());
        if (hechoPersistido != null) {
            hechoPersistido.getSolicitudesDeEliminacion().add(unaSolicitud);
            hechosRepository.save(hechoPersistido);
        }
    }

    @Override // todo: actualizar hechos si es que se aceptan las solicitudes
    public void procesarSolicitudes(List<Solicitud> unasSolicitudes, Usuario usuarioModificador) {
        for (Solicitud solicitudIndice : unasSolicitudes) {
            boolean esSpam = verificacionDeSpam(solicitudIndice);
            if (esSpam) { // TODO: SI ES SPAM, NO SE NECESITA UN USUARIO QUE LA RECHAZE
                solicitudIndice.setEstado(EstadoDeSolicitudDeEliminacion.RECHAZADA);
                this.actualizarHistorialDe(solicitudIndice, usuarioModificador);
                solicitudesRepository.save(solicitudIndice);
            }

            if(solicitudIndice.getJustificacionDeEliminacion().length() > 500) {
                solicitudIndice.setEstado(EstadoDeSolicitudDeEliminacion.APROBADA);
                this.actualizarHistorialDe(solicitudIndice, usuarioModificador);
            } else {
                solicitudIndice.setEstado(EstadoDeSolicitudDeEliminacion.RECHAZADA);
                this.actualizarHistorialDe(solicitudIndice, usuarioModificador);
            }

            solicitudesRepository.save(solicitudIndice);
        }
    }

    public boolean verificacionDeSpam(Solicitud unaSolicitud) {
        if (unaSolicitud.getDetectorDeSpam().esSpam("TEXTO DE SPAM")) {
            return true;
            // TODO: VER ALGORITMO TF-IDF
        } else {
            return false;
        }
    }

    public void actualizarHistorialDe(Solicitud unaSolicitud, Usuario usuarioModificador) {
        HistorialSolicitud historialSolicitud = new HistorialSolicitud(
                unaSolicitud.getEstado(),
                usuarioModificador
        );
    }
}
