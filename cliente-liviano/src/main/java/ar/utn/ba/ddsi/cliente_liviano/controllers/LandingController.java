package ar.utn.ba.ddsi.cliente_liviano.controllers;

import ar.utn.ba.ddsi.cliente_liviano.models.dtos.HechoInputDTO;
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

    @GetMapping
    public String landing(Model model) {
        List<HechoInputDTO> destacados = agregador.obtenerHechos(); // PUSE ESTE MÉTODO COMO PRUEBA,,,,, HAY Q CAMBIARLO, EN EL AGREGADOR TENDRRÍA Q HABER UNO Q DEUVLEVA DESTACADOS
        model.addAttribute("destacados", destacados);

        return "index";
    }
}
