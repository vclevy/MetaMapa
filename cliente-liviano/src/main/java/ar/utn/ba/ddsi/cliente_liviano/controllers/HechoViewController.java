package ar.utn.ba.ddsi.cliente_liviano.controllers;

import ar.utn.ba.ddsi.cliente_liviano.models.dtos.HechoInputDTO;
import ar.utn.ba.ddsi.cliente_liviano.services.impl.AgregadorService;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/hechos")
public class HechoViewController {
    @Autowired
    private final AgregadorService agregador;
    private static final int PAGE_SIZE = 10; // cantidad de hechos por página

    public HechoViewController(AgregadorService agregador) {
        this.agregador = agregador;
    }

    @GetMapping
    public String listar(Model model) {
        List<HechoInputDTO> hechos = agregador.obtenerHechos(); // trae todos
        model.addAttribute("hechos", hechos);

        // cálculo de total de páginas
        int totalHechos = hechos.size();
        int totalPaginas = (int) Math.ceil((double) totalHechos / PAGE_SIZE);
        model.addAttribute("totalPaginas", totalPaginas);

        return "listadoHechos";
    }
}

