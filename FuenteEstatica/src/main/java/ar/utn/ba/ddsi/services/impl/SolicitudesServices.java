package ar.utn.ba.ddsi.services.impl;


import ar.utn.ba.ddsi.models.dtos.output.HistorialSolicitudOutputDTO;
import ar.utn.ba.ddsi.models.dtos.output.SolicitudOutputDTO;
import ar.utn.ba.ddsi.models.entities.Hecho;
import ar.utn.ba.ddsi.models.hecho.solicitudes.EstadoDeSolicitudDeEliminacion;
import ar.utn.ba.ddsi.models.hecho.solicitudes.HistorialSolicitud;
import ar.utn.ba.ddsi.models.hecho.solicitudes.Solicitud;
import ar.utn.ba.ddsi.models.repositories.ISolicitudesRepository;
import ar.utn.ba.ddsi.services.ISolicitudesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Service
public class SolicitudesServices implements ISolicitudesServices {
    @Autowired
    private ISolicitudesRepository repositorioSolicitudes;

    private SolicitudOutputDTO solicitudOutputDTO(Solicitud solicitud) {
        SolicitudOutputDTO dto = new SolicitudOutputDTO();
        dto.setId(solicitud.getId());
        dto.setJustificacionDeEliminacion(solicitud.getJustificacionDeEliminacion());
        dto.setIdHecho(solicitud.getHecho().getId());
        dto.setEstado(solicitud.getEstado().name());
        dto.setFechaSolicitud(solicitud.getFechaSolicitud());
        dto.setFechaDeEvaluacionDeSolicitud(solicitud.getFechaDeEvaluacionDeSolicitud());
        dto.setVisitante(solicitud.getVisitanteQueCargoLaSolicitud().getNombre());
        List<HistorialSolicitudOutputDTO> historialDTOs = solicitud.getHistorialSolicitud().stream()
                .map(this::historialSolicitudOutputDTO)
                .collect(Collectors.toList());
        dto.setHistorialSolicitud(historialDTOs);
        return dto;
    }

    private HistorialSolicitudOutputDTO historialSolicitudOutputDTO(HistorialSolicitud historial) {
        HistorialSolicitudOutputDTO dto = new HistorialSolicitudOutputDTO();
        dto.setEstado(historial.getEstado().name());
        dto.setFechaModificacion(historial.getFechaModificacion());
        dto.setAdministrador(historial.getAdministradorModificador().getNombre());
        return dto;
    }

    public void solicitarEliminacionDe(Hecho unHecho) {
        if (!unHecho.getFueEliminado()) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Ingrese justificación de eliminación (mínimo 500 caracteres): ");
            String justificacion = scanner.nextLine().trim();

            if (justificacion.length() >= 500) {
                Solicitud solicitud = new Solicitud(unHecho, justificacion);
                unHecho.agregarSolicitudDeEliminacion(solicitud);
                repositorioSolicitudes.save(solicitud);
                solicitud.setEstado(EstadoDeSolicitudDeEliminacion.PENDIENTE);
                System.out.println("Solicitud de eliminación registrada.");
            } else {
                throw new IllegalArgumentException("La justificación debe tener al menos 500 caracteres.");
            }
        } else {
            throw new IllegalStateException("El hecho ya fue eliminado.");
        }
    }

    public void aprobarSolicitud(Solicitud unaSolicitud) {
        unaSolicitud.setEstado(EstadoDeSolicitudDeEliminacion.APROBADA);
    }

    public void rechazarSolicitud(Solicitud unaSolicitud) {
        unaSolicitud.setEstado(EstadoDeSolicitudDeEliminacion.RECHAZADA);
    }
}
