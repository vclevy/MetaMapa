package ar.utn.ba.ddsi.controllers;

import ar.utn.ba.ddsi.models.dtos.input.solicitud.SolicitudInputDTO;
import ar.utn.ba.ddsi.models.entities.usuario.Usuario;
import ar.utn.ba.ddsi.services.solicitudService.ISolcitudesService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/solicitud")
public class SolicitudController {

    private final ISolcitudesService solcitudesService;

    public SolicitudController(ISolcitudesService solcitudesService) {
        this.solcitudesService = solcitudesService;
    }

    // TODO: @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{idSolicitud}")
    public void procesarSolicitudDeEliminacion(@PathVariable Long idSolicitud, @RequestBody Usuario usuario, @RequestParam String accion) { //TODO: NO DEBERIA SER ASI, EL USUARIO TIENE QUE VENIR POR INTERFAZ
        this.solcitudesService.cambiarEstadoDeSolicitud(idSolicitud, usuario, accion);
    }

    @PostMapping("/")
    public void crearSolicitud(@RequestBody SolicitudInputDTO solicitud, Usuario usuario) { //TODO: NO DEBERIA SER ASI, EL USUARIO TIENE QUE VENIR POR INTERFAZ
        this.solcitudesService.registrarSolicitud(solicitud, usuario);
    }
}
