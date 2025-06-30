package ar.utn.ba.ddsi.services.solicitudService;

import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.models.entities.solicitud.EstadoDeSolicitudDeEliminacion;
import ar.utn.ba.ddsi.models.entities.solicitud.HistorialSolicitud;
import ar.utn.ba.ddsi.models.entities.solicitud.Solicitud;
import ar.utn.ba.ddsi.models.entities.usuario.Usuario;
import ar.utn.ba.ddsi.models.repositories.IHechosRepository;
import ar.utn.ba.ddsi.models.repositories.ISolicitudesRepository;
import ar.utn.ba.ddsi.services.hechoService.IHechoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class SolicitudesService implements ISolcitudesService {
    @Autowired
    private ISolicitudesRepository solicitudesRepository;

    @Autowired
    private IHechosRepository hechosRepository;

    @Override
    public void registrarSolicitud(String unaJustificacion, Long idHecho, Usuario unUsuario) {

        if (!this.justificacionTieneLongitudValida(unaJustificacion)) {
            return;
        }

        Solicitud unaSolicitud = new Solicitud(unaJustificacion, idHecho, unUsuario);
        unaSolicitud.setId(this.definirId());

        if (!this.verificacionDeSpam(unaSolicitud)) {
            unaSolicitud.setEstado(EstadoDeSolicitudDeEliminacion.PENDIENTE);
            this.actualizarHistorialDe(unaSolicitud.getId(), unUsuario);
            this.hechosRepository.findById(unaSolicitud.getIdHecho()).getSolicitudesDeEliminacion().add(unaSolicitud);
            solicitudesRepository.save(unaSolicitud);
        } else {
            unaSolicitud.setEstado(EstadoDeSolicitudDeEliminacion.RECHAZADA);
        }
    }

    @Override
    public void cambiarEstadoDeSolicitud(Long idSolicitud, Usuario usuarioModificador, String unaAccion) {
        List<Solicitud> solcicitudes = solicitudesRepository.findAll();

        for (Solicitud solicitudIndice : solcicitudes) {
            if (solicitudIndice.getId() == idSolicitud) {
                switch (unaAccion.toLowerCase()) {
                    case "aprobar":
                        solicitudIndice.setEstado(EstadoDeSolicitudDeEliminacion.APROBADA);
                        break;
                    case "rechazar":
                        solicitudIndice.setEstado(EstadoDeSolicitudDeEliminacion.RECHAZADA);
                        break;
                }
                this.actualizarHistorialDe(idSolicitud, usuarioModificador);
            }
        }
    }

    @Override
    public boolean verificacionDeSpam(Solicitud unaSolicitud) {
        if (unaSolicitud.getDetectorDeSpam().esSpam(unaSolicitud.getJustificacionDeEliminacion())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Long definirId() {
        List<Solicitud> solicitudesDeRepositorio = this.solicitudesRepository.findAll();

        Long maxId = solicitudesDeRepositorio
                .stream()
                .map(Solicitud::getId)
                .filter(Objects::nonNull)
                .max(Long::compareTo)
                .orElse(0L);

        return maxId + 1;
    }

    @Override
    public boolean justificacionTieneLongitudValida(String unaJustificacion) {
        return unaJustificacion != null && unaJustificacion.length() >= 500;
    }

    @Override
    public void actualizarHistorialDe(Long idSolicitud, Usuario usuarioModificador) {
        List<Solicitud> solcicitudes = solicitudesRepository.findAll();

        for (Solicitud solicitudIndice : solcicitudes) {
            if (solicitudIndice.getId() == idSolicitud) {
                HistorialSolicitud historialSolicitud = new HistorialSolicitud(
                        solicitudIndice.getEstado(),
                        usuarioModificador
                );
                solicitudIndice.getHistorialSolicitud().add(historialSolicitud);
            }
        }
    }
}
