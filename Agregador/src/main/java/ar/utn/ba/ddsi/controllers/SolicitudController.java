package ar.utn.ba.ddsi.controllers;

import ar.utn.ba.ddsi.models.dtos.input.solicitud.SolicitudInputDTO;
import ar.utn.ba.ddsi.models.dtos.output.SolicitudOutputDTO;
import ar.utn.ba.ddsi.services.solicitudService.ISolicitudesService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
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
    @PatchMapping("/{idSolicitud}/aprobar")
    public void aprobarSolicitud(@PathVariable Long idSolicitud,
                                 @RequestBody String usuarioModificador) {
        solicitudesService.aprobarSolicitud(idSolicitud, usuarioModificador);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{idSolicitud}/rechazar")
    public void rechazarSolicitud(@PathVariable Long idSolicitud,
                                  @RequestBody String usuarioModificador) {
        solicitudesService.rechazarSolicitud(idSolicitud, usuarioModificador);
    }

    @PostMapping()
    public void crearSolicitud(@RequestBody SolicitudInputDTO solicitud) {
        this.solicitudesService.registrarSolicitud(solicitud);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public List<SolicitudOutputDTO> obtenerTodas() {
        return solicitudesService.obtenerSolicitudes();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/pendientes")
    public List<SolicitudOutputDTO> obtenerPendientes() {
        return solicitudesService.obtenerSolicitudesPendientes();
    }

}
