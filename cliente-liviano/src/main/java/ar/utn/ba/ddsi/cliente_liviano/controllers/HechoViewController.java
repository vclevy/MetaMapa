package ar.utn.ba.ddsi.cliente_liviano.controllers;

import ar.utn.ba.ddsi.cliente_liviano.models.dtos.HechoInputDTO;
import ar.utn.ba.ddsi.cliente_liviano.services.impl.AgregadorService;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
    public String listar(
            @RequestParam(required = false) String fechaDesde,
            @RequestParam(required = false) String fechaHasta,
            @RequestParam(required = false) String ubicacion,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) String fuente,
            Model model) {

        List<HechoInputDTO> hechos = agregador.obtenerHechos(); // trae todos

        // Filtrado por fecha
        if (fechaDesde != null && !fechaDesde.isEmpty()) {
            hechos = hechos.stream()
                    .filter(h -> !h.getFechaDeAcontecimiento().isBefore(LocalDate.parse(fechaDesde).atStartOfDay()))
                    .toList();
        }
        if (fechaHasta != null && !fechaHasta.isEmpty()) {
            hechos = hechos.stream()
                    .filter(h -> !h.getFechaDeAcontecimiento().isAfter(LocalDate.parse(fechaHasta).atStartOfDay()))
                    .toList();
        }

        // Filtrado por ubicación
        if (ubicacion != null && !ubicacion.isEmpty()) {
            hechos = hechos.stream()
                    .filter(h -> h.getLugar().getProvincia().toLowerCase().contains(ubicacion.toLowerCase()))
                    .toList();
        }

        // Filtrado por categoría
        if (categoria != null && !categoria.isEmpty() && !categoria.equalsIgnoreCase("todas")) {
            hechos = hechos.stream()
                    .filter(h -> h.getCategoria().getNombre().equalsIgnoreCase(categoria))
                    .toList();
        }

//        // Filtrado por fuente
//        if (fuente != null && !fuente.isEmpty() && !fuente.equalsIgnoreCase("todas")) {
//            hechos = hechos.stream()
//                    .filter(h -> h.getFuente().equalsIgnoreCase(fuente))
//                    .toList();
//        }

        model.addAttribute("hechos", hechos);
        model.addAttribute("fechaDesde", fechaDesde);
        model.addAttribute("fechaHasta", fechaHasta);
        model.addAttribute("ubicacion", ubicacion);
        model.addAttribute("categoria", categoria);
        model.addAttribute("fuente", fuente);

        // Paginación
        int totalHechos = hechos.size();
        int totalPaginas = (int) Math.ceil((double) totalHechos / PAGE_SIZE);
        model.addAttribute("totalPaginas", totalPaginas);

        return "listadoHechos";
    }

}

