package ar.utn.ba.ddsi.cliente_liviano.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorPageController {

    @GetMapping("/error/403")
    public String error403(Model model) {
        model.addAttribute("mensaje", "No tenés permisos para acceder a esta página.");
        return "error/403";
    }

    @GetMapping("/error/401")
    public String error401(Model model) {
        model.addAttribute("mensaje", "Sesión expirada o acceso no autorizado.");
        return "error/401";
    }

    @GetMapping("/error/500")
    public String error500(Model model) {
        model.addAttribute("mensaje", "Ocurrió un error inesperado.");
        return "error/500";
    }
}

