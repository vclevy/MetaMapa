package ar.utn.ba.ddsi.services.solicitudService;

import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.models.entities.solicitud.EstadoDeSolicitudDeEliminacion;
import ar.utn.ba.ddsi.models.entities.solicitud.Solicitud;
import ar.utn.ba.ddsi.models.entities.usuario.Usuario;
import ar.utn.ba.ddsi.models.repositories.ISolicitudesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class SolicitudesService implements ISolcitudesService {
    @Autowired
    private ISolicitudesRepository solicitudesRepository;

    @Override
    public void registrarSolicitud(String unaJustificacion, Hecho unHecho, Usuario unUsuario) {

        if (!this.justificacionTieneLongitudValida(unaJustificacion)) {
            return;
        }

        Solicitud unaSolicitud = new Solicitud(unaJustificacion, unHecho, unUsuario);
        unaSolicitud.setId(this.definirId());

        if (!this.verificacionDeSpam(unaSolicitud)) {
            unaSolicitud.setEstado(EstadoDeSolicitudDeEliminacion.PENDIENTE);
            unaSolicitud.actualizarHistorialDe(unaSolicitud, unUsuario);
            unaSolicitud.getHecho().getSolicitudesDeEliminacion().add(unaSolicitud);
            solicitudesRepository.save(unaSolicitud);

        } else {
            unaSolicitud.setEstado(EstadoDeSolicitudDeEliminacion.RECHAZADA);
        }
    }

    @Override
    public void aprobarSolicitud(Solicitud unaSolicitud, Usuario usuarioModificador) {
        List<Solicitud> solcicitudes = solicitudesRepository.findAll();

        for (Solicitud solicitudIndice : solcicitudes) {
            if (solicitudIndice.getId() == unaSolicitud.getId()) {
                solicitudIndice.setEstado(EstadoDeSolicitudDeEliminacion.APROBADA);
                solicitudIndice.actualizarHistorialDe(unaSolicitud, usuarioModificador);
            }
        }
    }

    @Override
    public void rechazarSolicitud(Solicitud unaSolicitud, Usuario usuarioModificador) {
        List<Solicitud> solcicitudes = solicitudesRepository.findAll();

        for (Solicitud solicitudIndice : solcicitudes) {
            if (solicitudIndice.getId() == unaSolicitud.getId()) {
                solicitudIndice.setEstado(EstadoDeSolicitudDeEliminacion.RECHAZADA);
                solicitudIndice.actualizarHistorialDe(unaSolicitud, usuarioModificador);
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
}
