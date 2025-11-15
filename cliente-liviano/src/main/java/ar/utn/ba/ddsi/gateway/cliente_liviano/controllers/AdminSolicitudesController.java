package ar.utn.ba.ddsi.gateway.cliente_liviano.controllers;

import ar.utn.ba.ddsi.gateway.cliente_liviano.models.dtos.SolicitudDTO;
import ar.utn.ba.ddsi.gateway.cliente_liviano.services.impl.AgregadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/solicitudes")
public class AdminSolicitudesController {

    @Autowired
    private AgregadorService agregadorService;

    @GetMapping
    public String listarSolicitudes(Model model) {
        List<SolicitudDTO> solicitudes = agregadorService.obtenerSolicitudesPendientes();

        model.addAttribute("solicitudes", solicitudes);
        return "adminSolicitudes";
    }


    @PostMapping("/{id}/aprobar")
    public String aprobarSolicitud(@PathVariable Long id,
                                   @RequestParam String usuarioModificador,
                                   RedirectAttributes redirectAttrs) {
        try {
            agregadorService.aprobarSolicitud(id, usuarioModificador);
            redirectAttrs.addFlashAttribute("mensajeExito", "Solicitud aprobada correctamente.");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("mensajeError", "Error al aprobar la solicitud: " + e.getMessage());
        }
        return "redirect:/admin/solicitudes";
    }

    @PostMapping("/{id}/rechazar")
    public String rechazarSolicitud(@PathVariable Long id,
                                    @RequestParam String usuarioModificador,
                                    RedirectAttributes redirectAttrs) {
        try {
            agregadorService.rechazarSolicitud(id, usuarioModificador);
            redirectAttrs.addFlashAttribute("mensajeExito", "Solicitud rechazada correctamente.");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("mensajeError", "Error al rechazar la solicitud: " + e.getMessage());
        }
        return "redirect:/admin/solicitudes";
    }
}
