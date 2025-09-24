package ar.utn.ba.ddsi.cliente_liviano.controllers;

import ar.utn.ba.ddsi.cliente_liviano.models.dtos.ColeccionDTO;
import ar.utn.ba.ddsi.cliente_liviano.models.dtos.HechoDTO;
import ar.utn.ba.ddsi.cliente_liviano.services.impl.AgregadorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class LandingController {

    private final AgregadorService agregador;

    public LandingController(AgregadorService agregador) {
        this.agregador = agregador;
    }

    @GetMapping("/")
    public String landing(Model model) {
        // Lista de hechos destacados (solo algunos)
        List<HechoDTO> destacados = agregador.obtenerHechosDestacados(); //TODO
        model.addAttribute("destacados", destacados);

        // Lista de todos los hechos para el mapa
        List<HechoDTO> hechos = agregador.obtenerHechos();
        model.addAttribute("hechos", hechos);

        List<ColeccionDTO> coleccionesDestacadas = agregador.obtenerColecciones(); // TODO Q SEAN DESTACADAS
        model.addAttribute("coleccionesDestacadas", coleccionesDestacadas);

        return "index"; // Thymeleaf usará "destacados" y "hechos"
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        // TODO Por ahora los datos del dashboard están HARDCODEADOS en la vista.
        // Más adelante los agregamos dinámicamente con model.addAttribute(...)
        return "adminLanding";
    }
}
