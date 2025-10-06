package ar.utn.ba.ddsi.controllers;

import ar.utn.ba.ddsi.models.dtos.input.solicitud.SolicitudInputDTO;
import ar.utn.ba.ddsi.models.entities.solicitud.AccionesSolicitud;
import ar.utn.ba.ddsi.models.entities.solicitud.Solicitud;
import ar.utn.ba.ddsi.services.solicitudService.ISolicitudesService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/solicitud")
public class SolicitudController {

    private final ISolicitudesService solicitudesService;

    public SolicitudController(ISolicitudesService solcitudesService) {
        this.solicitudesService = solcitudesService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{idSolicitud}")
    public void procesarSolicitudDeEliminacion(@PathVariable Long idSolicitud, @RequestBody String usuarioModificador, @RequestParam AccionesSolicitud unaAccion) {
        this.solicitudesService.cambiarEstadoDeSolicitud(idSolicitud, usuarioModificador, unaAccion);
    }

    @PostMapping()
    public void crearSolicitud(@RequestBody SolicitudInputDTO solicitud) {
        this.solicitudesService.registrarSolicitud(solicitud);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public List<Solicitud> obtenerTodas(){
        return this.solicitudesService.obtenerSolicitudes();
    }
}
