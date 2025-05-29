package ar.utn.ba.ddsi.services.solicitudService;

import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.models.entities.solicitud.EstadoDeSolicitudDeEliminacion;
import ar.utn.ba.ddsi.models.entities.solicitud.HistorialSolicitud;
import ar.utn.ba.ddsi.models.entities.solicitud.Solicitud;
import ar.utn.ba.ddsi.models.entities.usuario.Usuario;
import ar.utn.ba.ddsi.models.repositories.IHechosRepository;
import ar.utn.ba.ddsi.models.repositories.ISolicitudesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SolicitudesService implements ISolcitudesService {
    @Autowired
    private ISolicitudesRepository solicitudesRepository;

    @Autowired
    private IHechosRepository hechosRepository;

    @Override
    public void registrarSolicitud(String unaJustificacion, Hecho unHecho, Usuario unUsuario) {
        Solicitud unaSolicitud = this.crearSolicitud(unaJustificacion, unHecho, unUsuario);
        // VERIFICO QUE LA SOLICITUD NO SEA SPAM
        if (!this.verificacionDeSpam(unaSolicitud)) {
            // GUARDO SOLICITUD EN EL REPO DE SOLICITUDES
            unaSolicitud.setEstado(EstadoDeSolicitudDeEliminacion.PENDIENTE);
            this.actualizarHistorialDe(unaSolicitud, unUsuario);
            solicitudesRepository.save(unaSolicitud);

            // GUARDO EN EL HISTORIAL DE SOLICITUDES DEL HECHO EN EL REPO DE HECHOS
            Hecho hechoPersistido = hechosRepository.findById(unaSolicitud.getHecho().getIdAgregador());
            if (hechoPersistido != null) {
                hechoPersistido.getSolicitudesDeEliminacion().add(unaSolicitud);
                hechosRepository.save(hechoPersistido);
            }
        } else {
            unaSolicitud.setEstado(EstadoDeSolicitudDeEliminacion.RECHAZADA);
            throw new RuntimeException("La solicitud no puede ser registrada porque es Spam.");
        }
    }

    @Override
    public void aprobarSolicitud(Solicitud unaSolicitud, Usuario usuarioModificador) {
        unaSolicitud.setEstado(EstadoDeSolicitudDeEliminacion.APROBADA);
        this.actualizarHistorialDe(unaSolicitud, usuarioModificador);
        this.solicitudesRepository.save(unaSolicitud);
    }

    @Override
    public void rechazarSolicitud(Solicitud unaSolicitud, Usuario usuarioModificador) {
        unaSolicitud.setEstado(EstadoDeSolicitudDeEliminacion.RECHAZADA);
        this.actualizarHistorialDe(unaSolicitud, usuarioModificador);
        this.solicitudesRepository.save(unaSolicitud);
    }

    public boolean verificacionDeSpam(Solicitud unaSolicitud) {
        if (unaSolicitud.getDetectorDeSpam().esSpam("TEXTO DE SPAM")) {
            return true;
        } else {
            return false;
        }
    }

    public void actualizarHistorialDe(Solicitud unaSolicitud, Usuario usuarioModificador) {
        HistorialSolicitud historialSolicitud = new HistorialSolicitud(
                unaSolicitud.getEstado(),
                usuarioModificador
        );
        unaSolicitud.getHistorialSolicitud().add(historialSolicitud);
    }

    public Solicitud crearSolicitud(String unaJustificacion, Hecho unHecho, Usuario unUsuario) {
        return new Solicitud (unaJustificacion, unHecho, unUsuario);
    }
}
