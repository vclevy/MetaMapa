package ar.utn.ba.ddsi.services.solicitudService;

import ar.utn.ba.ddsi.models.dtos.input.solicitud.SolicitudInputDTO;
import ar.utn.ba.ddsi.models.entities.hecho.Hecho;
import ar.utn.ba.ddsi.models.entities.solicitud.EstadoDeSolicitudDeEliminacion;
import ar.utn.ba.ddsi.models.entities.solicitud.HistorialSolicitud;
import ar.utn.ba.ddsi.models.entities.solicitud.Solicitud;
import ar.utn.ba.ddsi.models.entities.usuario.Usuario;
import ar.utn.ba.ddsi.models.repositories.IHechosRepository;
import ar.utn.ba.ddsi.models.repositories.ISolicitudesRepository;
import ar.utn.ba.ddsi.models.repositories.IUsuariosRepository;
import ar.utn.ba.ddsi.services.spam.DetectorDeSpam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class SolicitudesService implements ISolicitudesService {
    @Autowired
    private ISolicitudesRepository solicitudesRepository;

    @Autowired
    private IHechosRepository hechosRepository;

    @Autowired
    private IUsuariosRepository usuarioRepository;

    @Autowired
    private DetectorDeSpam detectorDeSpam;

    @Override
    public void registrarSolicitud(SolicitudInputDTO solicitudInputDTO, Long idUsuario) {

        if (!this.justificacionTieneLongitudValida(solicitudInputDTO.getJustificacion())) {
            return;
        }

        Hecho hecho = hechosRepository.findById(solicitudInputDTO.getIdHecho())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Hecho no encontrado con id " + solicitudInputDTO.getIdHecho()));


        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con id " + idUsuario));


        Solicitud unaSolicitud = new Solicitud(
                solicitudInputDTO.getJustificacion(),
                hecho,
                usuario
        );

        if (!this.verificacionDeSpam(unaSolicitud)) {
            unaSolicitud.setEstado(EstadoDeSolicitudDeEliminacion.PENDIENTE);
            solicitudesRepository.save(unaSolicitud);
            this.actualizarHistorialDe(unaSolicitud.getIdSolicitud(), usuario);

        } else {
            unaSolicitud.setEstado(EstadoDeSolicitudDeEliminacion.RECHAZADA);
        }
    }

    @Override
    public void cambiarEstadoDeSolicitud(Long idSolicitud, Long idUsuarioModificador, String unaAccion) {
        Solicitud solicitud = solicitudesRepository.findById(idSolicitud)
                .orElseThrow(() -> new IllegalArgumentException("Solicitud no encontrada con id " + idSolicitud));

        Usuario usuarioModificador = usuarioRepository.findById(idUsuarioModificador)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con id " + idUsuarioModificador));


        switch (unaAccion.toLowerCase()) {
            case "aprobar":
                solicitud.setEstado(EstadoDeSolicitudDeEliminacion.APROBADA);
                break;
            case "rechazar":
                solicitud.setEstado(EstadoDeSolicitudDeEliminacion.RECHAZADA);
                break;
            default:
                throw new IllegalArgumentException("Acción inválida: " + unaAccion);
        }

        solicitud.setFechaDeEvaluacionDeSolicitud(LocalDateTime.now());

        this.actualizarHistorialDe(idSolicitud, usuarioModificador);

        solicitudesRepository.save(solicitud);
    }

    @Override
    public boolean verificacionDeSpam(Solicitud unaSolicitud) {
        return this.detectorDeSpam.esSpam(unaSolicitud.getJustificacionDeEliminacion());
    }



    @Override
    public boolean justificacionTieneLongitudValida(String unaJustificacion) {
        return unaJustificacion != null && unaJustificacion.length() >= 500;
    }

    @Override
    public void actualizarHistorialDe(Long idSolicitud, Usuario usuarioModificador) {
        List<Solicitud> solcicitudes = solicitudesRepository.findAll();

        for (Solicitud solicitudIndice : solcicitudes) {
            if (solicitudIndice.getIdSolicitud() == idSolicitud) {
                HistorialSolicitud historialSolicitud = new HistorialSolicitud(
                        solicitudIndice.getEstado(),
                        usuarioModificador
                );
                solicitudIndice.getHistorialSolicitud().add(historialSolicitud);
            }
        }
    }
}
