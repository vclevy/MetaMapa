package ar.utn.ba.ddsi.controllers;

import ar.utn.ba.ddsi.models.dtos.input.solicitud.SolicitudInputDTO;
import ar.utn.ba.ddsi.models.entities.solicitud.Solicitud;
import ar.utn.ba.ddsi.models.entities.usuario.Usuario;
import ar.utn.ba.ddsi.services.solicitudService.ISolicitudesService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/solicitud")
public class SolicitudController {

    private final ISolicitudesService solcitudesService;

    public SolicitudController(ISolicitudesService solcitudesService) {
        this.solcitudesService = solcitudesService;
    }

    // TODO: @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{idSolicitud}")
    public void procesarSolicitudDeEliminacion(@PathVariable Long idSolicitud, @RequestBody Long idUsuarioModificador, @RequestParam String accion) { //TODO: NO DEBERIA SER ASI, EL USUARIO TIENE QUE VENIR POR INTERFAZ
        this.solcitudesService.cambiarEstadoDeSolicitud(idSolicitud, idUsuarioModificador, accion);
    }

    @PostMapping()
    public void crearSolicitud(@RequestBody SolicitudInputDTO solicitud, @RequestParam Long idUsuario) { //TODO: NO DEBERIA SER ASI, EL USUARIO TIENE QUE VENIR POR INTERFAZ
        this.solcitudesService.registrarSolicitud(solicitud, idUsuario);
    }

    @GetMapping("/all")
    public List<Solicitud> obtenerTodas(){
        return this.solcitudesService.obtenerSolicitudes();
    }
}
