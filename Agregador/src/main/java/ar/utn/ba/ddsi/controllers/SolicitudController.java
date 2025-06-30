package ar.utn.ba.ddsi.controllers;

import ar.utn.ba.ddsi.models.entities.solicitud.Solicitud;
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
    @PatchMapping("/{id}/")
    public void cambiarEstadoSolicitud(@PathVariable Long idSolicitud, @RequestBody Usuario usuario, @RequestParam String accion) { //TODO: NO DEBERIA SER ASI, EL USUARIO TIENE QUE VENIR POR INTERFAZ
        this.solcitudesService.cambiarEstadoDeSolicitud(idSolicitud, usuario, accion);
    }

    @PostMapping("/")
    public void crearSolicitud(@RequestBody Solicitud solicitud, Usuario usuario) { //TODO: NO DEBERIA SER ASI, EL USUARIO TIENE QUE VENIR POR INTERFAZ
        this.solcitudesService.registrarSolicitud(solicitud.getJustificacionDeEliminacion(), solicitud.getIdHecho(), usuario);
    }
}
